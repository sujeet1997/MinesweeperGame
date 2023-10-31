package com.sujeet.minesweepergame

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

object CommonDialog {
    private var dialog: Dialog? = null

    interface CommonDialogCallBack {
        fun onOk()
    }

    fun showCommonDialog(
        context: Context,
        gameWon:Boolean,
        commonDialogCallBack: CommonDialogCallBack,
    ) {

        if (dialog != null) return

        dialog = Dialog(context, R.style.Theme_Dialog)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setGravity(Gravity.CENTER)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        val window = dialog!!.window
        window!!.setGravity(Gravity.CENTER)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog?.setContentView(R.layout.common_dialog)
        dialog?.show()

        val best_time = dialog?.findViewById<View>(R.id.best_time) as TextView
        val last_game_time = dialog?.findViewById<View>(R.id.last_game_time) as TextView
        val game_status = dialog?.findViewById<View>(R.id.game_status) as TextView
        val image = dialog?.findViewById<View>(R.id.image) as ImageView
        val ok_button = dialog?.findViewById<View>(R.id.ok_button) as LinearLayout

        if (gameWon){
            val sharedPrefs = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE)
            var bestScore:Int = sharedPrefs.getInt(BEST_SCORE,0)
            val prevScore:Int = sharedPrefs.getInt(PREV_SCORE,0)
            if((bestScore == 0 && prevScore != 0)|| prevScore < bestScore){
                bestScore = prevScore
            }

            best_time.visibility = View.VISIBLE
            last_game_time.visibility = View.VISIBLE
            image.setImageDrawable(context.resources.getDrawable(R.drawable.group_11))
            game_status.text = "Congratulation!\nYou Won"
            game_status.setTextColor(context.resources.getColor(R.color.green))
            best_time.text = "Best Time : " + bestScore.toString()
            last_game_time.text = "Last Game Time : " + prevScore.toString()

            with(sharedPrefs.edit()){
                putInt(BEST_SCORE,bestScore)
                apply()
            }
        }else{
            image.setImageDrawable(context.resources.getDrawable(R.drawable.failure))
            game_status.text = "Oops!\nYou Lost"
            game_status.setTextColor(context.resources.getColor(R.color.colorPrimary))
            best_time.visibility = View.GONE
            last_game_time.visibility = View.GONE

        }



        ok_button.setOnClickListener {
            commonDialogCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }



    }
}