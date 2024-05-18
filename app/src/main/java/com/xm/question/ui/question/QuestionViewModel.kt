package com.xm.question.ui.question

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xm.question.domain.usecase.QuestionFetcher
import com.xm.question.domain.usecase.SaveAnswer
import com.xm.question.ui.navigation.NavigationState
import com.xm.question.ui.question.model.AnswerItem
import com.xm.question.ui.question.model.IteratorOption
import com.xm.question.ui.question.model.QuestionAction
import com.xm.question.ui.question.model.QuestionState
import com.xm.question.util.Mapper.toDataModel
import com.xm.question.util.Mapper.toQuestionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    questionFetcher: QuestionFetcher,
    private val saveAnswer: SaveAnswer
) : ViewModel() {

    private val _sharedAnswerResult = MutableSharedFlow<Result<AnswerItem>>()
    val sharedAnswerResult = _sharedAnswerResult.asSharedFlow()

    private val fetcherFlow: Flow<QuestionState> = questionFetcher.invoke()
        .map { result ->
            if (result.isSuccess) {
                QuestionState(
                    result.getOrThrow().map { it.toQuestionItem() }
                )
            } else {
                QuestionState()
            }

        }
        .catch { emit(QuestionState()) }


    val state: StateFlow<QuestionState> = fetcherFlow
        .onEach { Log.d(TAG, "On receive new state.\n state.${it.items}") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = QuestionState()
        )

    private val _navigationFlow = MutableSharedFlow<NavigationState>()
    val navigationFlow = _navigationFlow.asSharedFlow()

    private val _currentPageState = MutableStateFlow(0)
    val currentPageState = _currentPageState.asStateFlow()

    fun sendAction(action: QuestionAction) {
        Log.d(TAG, "Handling view event. action: $action")

        when (action) {
            QuestionAction.Back -> navigateUp()
            is QuestionAction.PageChange -> {
                val newPage = when (action.option) {
                    IteratorOption.PREVIOUS -> currentPageState.value - 1
                    IteratorOption.NEXT -> currentPageState.value + 1
                }
                viewModelScope.launch {
                    _currentPageState.emit(newPage)
                }
            }

            is QuestionAction.SubmitAnswer -> {
                submitAnswer(action.answerItem)
            }
        }
    }

    private fun navigateUp() = viewModelScope.launch {
        _navigationFlow.emit(NavigationState.BackStack)
    }

    private fun submitAnswer(answerItem: AnswerItem) = viewModelScope.launch {
        saveAnswer(answerItem.toDataModel())
            .map { it.map { answerItem } }
            .onEach { _sharedAnswerResult.emit(it) }
            .launchIn(viewModelScope)
    }

    companion object {
        private val TAG = QuestionViewModel::class.java.simpleName
    }
}


