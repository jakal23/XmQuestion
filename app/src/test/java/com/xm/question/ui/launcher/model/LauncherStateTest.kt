package com.xm.question.ui.launcher.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.Exception

class LauncherStateTest {

    @Test
    fun testIdleState() {
        val state = LauncherState.Idle
        assertFalse(state.isSuccess())
        assertFalse(state.isFailure())
        assertFalse(state.isLoading())
    }

    @Test
    fun testLoadingState() {
        val state = LauncherState.Loading
        assertFalse(state.isSuccess())
        assertFalse(state.isFailure())
        assertTrue(state.isLoading())
    }

    @Test
    fun testCompleteSuccessState() {
        val result = Result.success(Unit)
        val state = LauncherState.Complete(result)
        assertTrue(state.isSuccess())
        assertFalse(state.isFailure())
        assertFalse(state.isLoading())
    }

    @Test
    fun testCompleteFailureState() {
        val result = Result.failure<Unit>(Exception("Error"))
        val state = LauncherState.Complete(result)
        assertFalse(state.isSuccess())
        assertTrue(state.isFailure())
        assertFalse(state.isLoading())
    }
}
