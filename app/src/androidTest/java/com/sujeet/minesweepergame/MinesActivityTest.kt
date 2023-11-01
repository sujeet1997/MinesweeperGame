package com.sujeet.minesweepergame

import android.view.LayoutInflater
import androidx.test.platform.app.InstrumentationRegistry
import com.sujeet.minesweepergame.databinding.ActivityMinesBinding
import com.sujeet.minesweepergame.view.gameScreen.MinesActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MinesActivityTest{

    @Mock
    private lateinit var activity: MinesActivity

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val layoutInflater = LayoutInflater.from(InstrumentationRegistry.getInstrumentation().targetContext)
        val binding = ActivityMinesBinding.inflate(layoutInflater)
        activity.binding = binding
    }

    @Test
    fun testStartTimer() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {

            Mockito.`when`(activity.startTimer()).thenCallRealMethod()

            // Create a timer
            val timer = activity.startTimer()
            
            // For example, check if the timer is not null
            assertNotNull(timer)

            // In this example, simulate 5 seconds of timer progress
            for (i in 0 until 5) {
                timer.onTick(1000) // Simulate one second passed
            }

            // Verify that the counter increased by 5 seconds
            assertEquals(5, activity.counter)

            timer.onFinish()

            // Verify that the timer finished and the counter was updated
            assertEquals(5, activity.counter)
        }


    }



}