package com.sujeet.minesweepergame

import android.widget.Button

data class MineButton(
    val btn:Button,
    val i:Int,
    val j:Int,
    var count:Int = 0,
    var isMine:Boolean = false,
    var isFlagged:Boolean = false
)
