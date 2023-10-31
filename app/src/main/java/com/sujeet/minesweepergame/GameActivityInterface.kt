package com.sujeet.minesweepergame

import android.os.CountDownTimer

interface GameActivityInterface {
    fun startTimer(): CountDownTimer
    fun initializeMines(mines: Int)
    fun whenBtnClicked(obj: MineButton)
    fun rGameAlgo(i: Int, j: Int)
    fun checkCompleteConventional()
    fun disableAll(gameWon: Boolean)
    fun restartGame()
    fun enableAll()
}