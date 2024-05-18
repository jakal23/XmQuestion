package com.xm.question.ui.navigation

import androidx.navigation.NavHostController
import com.xm.question.ui.question.navQuestionScreen

fun NavHostController.navigate(navigationState: NavigationState) {
    when (navigationState) {
        NavigationState.QuestionList -> navQuestionScreen()
        NavigationState.BackStack -> navigateUp()
    }
}