package com.xm.question.ui.launcher

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.xm.question.ui.launcher.model.FetchAction
import com.xm.question.ui.navigation.navigate
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.launcherScreenNavigation(navController: NavHostController) {
    composable(ROUTE_LAUNCHER) {
        LauncherScreenNavigation(navController)
    }
}


@Composable
fun LauncherScreenNavigation(
    navController: NavHostController,
    viewModel: LauncherViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.navigationFlow.collectLatest {
            navController.navigate(it)
        }
    }

    LauncherScreen(
        modifier = Modifier,
        state = state,
        submit = { viewModel.sendAction(FetchAction.Submit) },
        retry = { viewModel.sendAction(FetchAction.Retry) },
    )
}

const val ROUTE_LAUNCHER = "/"