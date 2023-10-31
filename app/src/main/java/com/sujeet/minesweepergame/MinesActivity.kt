package com.sujeet.minesweepergame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import com.sujeet.minesweepergame.databinding.ActivityMinesBinding

const val BEST_SCORE = "BEST_SCORE"
const val PREV_SCORE = "PREV_SCORE"
const val SHARED_PREF_NAME = "Game"

open class MinesActivity : AppCompatActivity(),GameActivityInterface  {
    internal lateinit var binding: ActivityMinesBinding

    internal var rows = -1
    internal var columns = -1
    internal var mines = -1

    lateinit var timer:CountDownTimer
    internal var counter = 0

    private var nonMines : Int = -1
    internal var actualMineCount : Int = 0
    private var flagMineCount : Int = -1

    private var arrMines :MutableList<MutableList<MineButton>> = mutableListOf()

    private var clickCount = 0 // imp for checking completion of game // restart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rows = 8
        columns = 8
        mines = 16

        initializeArrData()
        initializeMines(mines) // sets some random pixes as mines and increases adjacent pixes count by 1

        // counting no of nonMines
        initializeNonMines()

        // Total Mines count to Display in MineCount
        initializeTextViewMines()

        // setting up clicklisteners to every pixel
        settingUpListeners() // sets up listeners for the buttons

        timer = startTimer()
        timer.start()

        binding.buttonRestart.setOnClickListener {
            restartGame()
        }
    }

    private fun initializeArrData(){
        // params for linear layout
        val params1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0
        ).apply {
            weight = 1.0F
        }
        val params2 = LinearLayout.LayoutParams(
            0,
            150
        ).apply {
            weight = 1.0F
        }
        for(i in 0 until rows){
            val horLinearLayout = LinearLayout(this)
            horLinearLayout.layoutParams = params1
            val arr = mutableListOf<MineButton>()
            for(j in 0 until columns){
                val btnMine = Button(this)
//                btnMine.setBackgroundColor(resources.getColor(R.color.purple_700))
                btnMine.setBackgroundResource(R.drawable.button_border)
                btnMine.id = (i.toString()+j.toString()).toInt()
                btnMine.layoutParams = params2
                val btnObj = MineButton(btnMine,i,j)
                arr.add(btnObj)
                horLinearLayout.addView(btnMine)
            }
            binding.innerLinearLayout.addView(horLinearLayout)
            arrMines.add(arr)
        }
        Log.i("GAME_TAG",arrMines.toString())
    }

    private fun settingUpListeners(){
        // setting click listeners and Long click listeners
        for(i in 0 until rows){
            for(j in 0 until columns){
                if(arrMines[i][j].isMine){
                    arrMines[i][j].btn.setBackgroundColor(resources.getColor(R.color.black))
                }
                arrMines[i][j].btn.setOnClickListener {
                    whenBtnClicked(arrMines[i][j])
                }
            }
        }
    }

    override fun startTimer(): CountDownTimer {
        val timer = object : CountDownTimer(1200000,1000){
            override fun onTick(p0: Long) {
                binding.timer.text = counter.toString()
                counter++
            }

            override fun onFinish() {
                binding.timer.text = counter.toString()
            }
        }
        return timer
    }

    override fun initializeMines(mines: Int) {
        for (l in 0 until mines) {
            val i = (0 until rows).random()
            val j = (0 until columns).random()
            if (!arrMines[i][j].isMine) {
                actualMineCount++
                arrMines[i][j].isMine = true

                if(i-1 >= 0 && j-1>=0){
                    arrMines[i-1][j-1].count++
                }
                if((i >= 0 || i < rows) && j-1 >= 0)
                    arrMines[i][j-1].count++
                if(i-1 >= 0 && (j >= 0 || j < columns))
                    arrMines[i-1][j].count++
                if(i-1 >= 0 && j+1<columns){
                    arrMines[i-1][j+1].count++
                }
                if((i >= 0 || i < rows) && j+1 < columns)
                    arrMines[i][j+1].count++
                if(i+1 < rows && j - 1 >= 0){
                    arrMines[i+1][j-1].count++
                }
                if(i+1<rows && (j >= 0 || j < columns))
                    arrMines[i+1][j].count++
                if(i+1<rows && j+1<columns)
                    arrMines[i+1][j+1].count++
            }
        }
    }

    override fun whenBtnClicked(obj: MineButton) {
        rGameAlgo(obj.i,obj.j)
        checkCompleteConventional()
    }

    override fun rGameAlgo(i: Int, j: Int) {
        if((i < 0 || i >= rows || j < 0 || j >= columns) || (arrMines[i][j].isFlagged)||(!arrMines[i][j].btn.isEnabled))
            return
        if(arrMines[i][j].isMine){
//            Toast.makeText(this,"Oops You Stepped on a Mine , YOU LOST !!", Toast.LENGTH_LONG).show()
            disableAll(false)
        }
        else if(arrMines[i][j].count > 0){
            clickCount++
            arrMines[i][j].btn.text = arrMines[i][j].count.toString()
            arrMines[i][j].btn.setTextColor(resources.getColor(R.color.white))
            arrMines[i][j].btn.setBackgroundColor(resources.getColor(R.color.purple_500))
            arrMines[i][j].btn.isEnabled = false
        }
        else if(arrMines[i][j].count == 0){
            clickCount ++
            arrMines[i][j].btn.text = "" // setting an empty string
            arrMines[i][j].btn.setBackgroundColor(resources.getColor(R.color.purple_500))
            arrMines[i][j].btn.isEnabled = false
            rGameAlgo(i-1,j)
            rGameAlgo(i,j+1)
            rGameAlgo(i+1,j)
            rGameAlgo(i,j-1)
        }
    }

    override fun checkCompleteConventional() {
        Log.i("GAME_TAG","Non Mines count : $nonMines , clickCounts : $clickCount")
        if(nonMines == clickCount){
//            Toast.makeText(this,"Congrats You won !",Toast.LENGTH_LONG).show()
            disableAll(true)
        }
    }

    override fun disableAll(gameWon: Boolean) {
        // disabling all buttons and setting mine_mine as background for mine buttons
        for(i in 0 until rows){
            for(j in 0 until columns){
                if(arrMines[i][j].isMine){
                    // set background as mine
                    arrMines[i][j].btn.setBackgroundResource(R.drawable.mine)
                }
                else if(!arrMines[i][j].isFlagged && arrMines[i][j].btn.isEnabled){
                    if(gameWon){
                        if(arrMines[i][j].count > 0)
                            arrMines[i][j].btn.text = arrMines[i][j].count.toString()
                    }
                    arrMines[i][j].btn.isEnabled = false
                }
            }
        }
        timer.cancel()
        if(gameWon){
            Log.i("GAME_TAG","Saving the prev score : ${counter-1}")
            // storing the the time in prevTime
            val sharedPref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            with(sharedPref.edit()){
                putInt(PREV_SCORE,counter-1)
                apply()
            }

            CommonDialog.showCommonDialog(this,true, object :CommonDialog.CommonDialogCallBack{
                override fun onOk() {
                    restartGame()
                }

            })

        }else{
            CommonDialog.showCommonDialog(this, false, object :CommonDialog.CommonDialogCallBack{
                override fun onOk() {
                    restartGame()
                }

            })
        }
    }

    override fun restartGame() {
        Log.i("GAME_TAG","Restarting the game !!")
        enableAll()
        clickCount = 0
        actualMineCount = 0
        counter = 0
        initializeMines(mines) // sets some random pixes as mines and increases adjacent pixes count by 1
        // counting no of nonMines
        initializeNonMines()
        // initializing textView Mines to no of mines
        initializeTextViewMines() // initialization of actual mine counts
        if (::timer.isInitialized){
            timer.cancel()
        }

        timer = startTimer()
        timer.start() // starting the timer
    }

    override fun enableAll() {
        for(i in 0 until rows){
            for(j in 0 until columns){
                arrMines[i][j].btn.isEnabled = true
                arrMines[i][j].isFlagged = false
                arrMines[i][j].isMine = false
                arrMines[i][j].count = 0
                arrMines[i][j].btn.text = ""
                arrMines[i][j].btn.setBackgroundResource(R.drawable.button_border)
            }
        }
    }

    private fun initializeNonMines(){
        nonMines = rows*columns - actualMineCount
        Log.i("GAME_TAG","nonMines : $nonMines")
    }

    private fun initializeTextViewMines(){
        binding.MineCount.text = actualMineCount.toString()
        flagMineCount = actualMineCount // this variable for checking flagged mines correctly
    }

}