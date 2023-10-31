package com.sujeet.minesweepergame

import android.view.LayoutInflater
import androidx.test.platform.app.InstrumentationRegistry
import com.sujeet.minesweepergame.databinding.ActivityMinesBinding
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
    // Mock your activity
    @Mock
    private lateinit var activity: MinesActivity

    @Before
    fun setUp() {
        // Initialize your activity here if needed
        // Example: activity = GameActivity()
        MockitoAnnotations.initMocks(this)

        // Initialize binding here (similar to how it's done in your real activity)
        val layoutInflater = LayoutInflater.from(InstrumentationRegistry.getInstrumentation().targetContext)
        val binding = ActivityMinesBinding.inflate(layoutInflater)
        activity.binding = binding
    }

    @Test
    fun testStartTimer() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            /// Mock the behavior of the startTimer method
            Mockito.`when`(activity.startTimer()).thenCallRealMethod()

            // Create a timer
            val timer = activity.startTimer()

            // Now, you can test the timer's behavior
            // For example, you can check if the timer is not null
            assertNotNull(timer)

            // You can also advance the timer to test its behavior
            // In this example, we simulate 5 seconds of timer progress
            for (i in 0 until 5) {
                timer.onTick(1000) // Simulate one second passed
            }

            // Verify that the counter increased by 5 seconds
            assertEquals(5, activity.counter)

            // You can also test the onFinish method
            timer.onFinish()

            // Verify that the timer finished and the counter was updated
            assertEquals(5, activity.counter)
        }


    }



}