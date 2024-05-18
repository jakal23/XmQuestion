package com.xm.question.ui.navigation

sealed interface NavigationState {
    data object QuestionList : NavigationState
    data object BackStack : NavigationState
}