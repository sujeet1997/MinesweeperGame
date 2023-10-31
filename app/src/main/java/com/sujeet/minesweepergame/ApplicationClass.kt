package com.sujeet.minesweepergame

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate

class ApplicationClass:Application() {
    companion object {
        // set application context
        lateinit var appContext: Context

        // Get or Set foreground activity
        @SuppressLint("StaticFieldLeak")
        var mCurrentActivity: Activity? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        // Internet checking
        /*** Hard Code ***/
//        NetworkXCore.init(this, NetworkXObservingStrategy.HIGH)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onTerminate() {
        Log.d("TAG", "onTerminate: KeshavCementTerminated")
        mCurrentActivity = null
        super.onTerminate()

    }
}