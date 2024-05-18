package com.xm.question.ui.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.xm.question.ui.navigation.navigate
import com.xm.question.ui.question.model.QuestionAction
import com.xm.question.ui.question.model.QuestionItem
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.questionScreenNavigation(navController: NavHostController) {
    composable(ROUTE_QUESTION) {
        QuestionScreenNavigation(navController)
    }
}

fun NavHostController.navQuestionScreen() {
    navigate(ROUTE_QUESTION)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionScreenNavigation(
    navController: NavHostController,
    viewModel: QuestionViewModel = hiltViewModel()
) {

    val state = remember { mutableStateListOf<QuestionItem>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val pageState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.size }
    )

    LaunchedEffect(true) {
        viewModel.currentPageState.collect {
            pageState.scrollToPage(it)
        }
    }

    LaunchedEffect(true) {
        viewModel.state.collectLatest {
            state.addAll(it.items)
        }
    }

    LaunchedEffect(true) {
        viewModel.navigationFlow.collectLatest {
            navController.navigate(it)
        }
    }

    LaunchedEffect(true) {
        viewModel.sharedAnswerResult.collectLatest { answerResponse ->
            if (answerResponse.isSuccess) {
                val answerItem = answerResponse.getOrThrow()
                val index = state.indexOfFirst { it.id == answerItem.questionId }
                state[index] = state[index].copy(answer = answerItem.answer)
            } else {
                snackbarHostState.showSnackbar("Save answer failed")
            }
        }
    }

    QuestionScreen(
        modifier = Modifier,
        questions = state,
        pagerState = pageState,
        hostState = snackbarHostState,
        navigateUp = { viewModel.sendAction(QuestionAction.Back) },
        onPageSelected = { viewModel.sendAction(QuestionAction.PageChange(it)) },
        onSubmitAnswer = { viewModel.sendAction(QuestionAction.SubmitAnswer(it)) }
    )
}


private const val ROUTE_QUESTION = "/question"