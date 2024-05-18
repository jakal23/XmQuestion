package com.xm.question.ui.launcher.model

sealed interface FetchAction {
    data object Submit : FetchAction
    data object Retry : FetchAction
}