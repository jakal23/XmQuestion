package com.xm.question.ui.launcher.model

sealed class LauncherState {
    data object Idle : LauncherState()
    data object Loading : LauncherState()
    data class Complete(val result: Result<Unit>) : LauncherState()

    fun isSuccess(): Boolean {
        return this is Complete && result.isSuccess
    }

    fun isFailure(): Boolean {
        return this is Complete && result.isFailure
    }

    fun isLoading(): Boolean {
        return this is Loading
    }
}