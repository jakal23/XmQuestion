package com.xm.question.ui.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xm.question.R
import com.xm.question.ui.compose.InputField
import com.xm.question.ui.navigation.AppBar
import com.xm.question.ui.question.model.AnswerItem
import com.xm.question.ui.question.model.IteratorOption
import com.xm.question.ui.question.model.QuestionItem
import com.xm.question.util.Mapper.asTitle
import com.xm.question.util.Mapper.toOptionItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun Preview() {
    val questions = (1..3).map {
        QuestionItem(it, "Question number $it", "Simple answer $it")
    }

    QuestionScreen(
        modifier = Modifier.fillMaxSize(),
        questions = questions,
        hostState = remember { SnackbarHostState() },
        pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { questions.size }
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
    questions: List<QuestionItem>,
    pagerState: PagerState,
    onPageSelected: (IteratorOption) -> Unit = {},
    onSubmitAnswer: (AnswerItem) -> Unit = {},
    navigateUp: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(
                title = pagerState.asTitle(),
                optionItems = listOf(
                    IteratorOption.PREVIOUS.toOptionItem(pagerState.currentPage > 0),
                    IteratorOption.NEXT.toOptionItem(pagerState.currentPage < questions.size - 1)
                ),
                onNavigationIconClick = { navigateUp() },
                onOptionItemClick = {
                    IteratorOption.findById(it)?.let { it1 -> onPageSelected(it1) }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState) }
    ) { paddingValues ->
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            state = pagerState
        ) { currentPage ->

            val questionItem = questions[currentPage]
            var answer by remember { mutableStateOf(questionItem.answer) }

            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedCard(Modifier.fillMaxWidth()) {
                    val submittedCount = questions.count { it.answer.isNotEmpty() }
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Questions submitted. $submittedCount"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = questionItem.question)

                Spacer(modifier = Modifier.height(16.dp))

                InputField(
                    value = answer,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = questionItem.answer.isEmpty(),
                    label = stringResource(R.string.type_your_answer_here)
                ) {
                    answer = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TAG_BTN_SUBMIT),
                    onClick = {
                        onSubmitAnswer(
                            AnswerItem(questionItem.id, answer)
                        )
                    },
                    enabled = answer.isNotEmpty() && questionItem.answer.isEmpty(),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = stringResource(R.string.submit))
                }
            }
        }
    }

}

const val TAG_BTN_SUBMIT = "btn_submit"