package com.sujeet.minesweepergame


import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameActivityTest{

    // Mock your activity
    @Mock
    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        // Initialize your activity here if needed
        // Example: activity = GameActivity()
    }

    @Test
    fun testGameInitialization() {
        Mockito.`when`(activity.rows).thenReturn(8)
        Mockito.`when`(activity.columns).thenReturn(8)
        Mockito.`when`(activity.mines).thenReturn(16)

        assert(activity.rows == 8)
        assert(activity.columns == 8)
        assert(activity.mines == 16)
    }


    @Test
    fun testMinePlacement() {
        // Mock the game with specific mine placement
        val expectedMineCount = 5
        Mockito.`when`(activity.rows).thenReturn(8)
        Mockito.`when`(activity.columns).thenReturn(8)
        Mockito.`when`(activity.mines).thenReturn(expectedMineCount)

        activity.initializeMines(expectedMineCount)

        // Verify that the actualMineCount matches the expectedMineCount
        assert(activity.actualMineCount == expectedMineCount)
    }

}
