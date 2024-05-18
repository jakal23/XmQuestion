package com.xm.question.ui.launcher

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xm.question.domain.usecase.QuestionFetcher
import com.xm.question.ui.launcher.model.FetchAction
import com.xm.question.ui.launcher.model.LauncherState
import com.xm.question.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    questionFetcher: QuestionFetcher
) : ViewModel() {

    private val retryFlow = MutableStateFlow(1)

    private val fetcherFlow: Flow<LauncherState> = questionFetcher.invoke()
        .onEach { Log.d(TAG, "On receive new state.\n state.$it") }
        .map {
            if (it.isSuccess) LauncherState.Complete(Result.success(Unit))
            else LauncherState.Complete(Result.failure(it.exceptionOrNull()!!))
        }
        .catch {
            emit(LauncherState.Complete(Result.failure(it)))
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<LauncherState> = retryFlow
        .flatMapMerge { fetcherFlow }
        .onStart { emit(LauncherState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = LauncherState.Idle
        )

    private val _navigationFlow = MutableSharedFlow<NavigationState>()
    val navigationFlow = _navigationFlow.asSharedFlow()

    fun sendAction(fetchAction: FetchAction) {
        Log.d(TAG, "Handling view event. action: $fetchAction")

        when (fetchAction) {
            FetchAction.Retry -> retryEvent()
            FetchAction.Submit -> navigateQuestionEvent()
        }
    }

    private fun retryEvent() = viewModelScope.launch {
        retryFlow.emit(
            retryFlow.value + 1
        )
    }

    private fun navigateQuestionEvent() = viewModelScope.launch {
        _navigationFlow.emit(NavigationState.QuestionList)
    }

    companion object {
        private val TAG = LauncherViewModel::class.java.simpleName
    }
}