package com.xm.question.domain.usecase

import android.util.Log
import com.xm.question.domain.ContractViolationException
import com.xm.question.domain.data.QuestionClient
import com.xm.question.domain.data.QuestionStorage
import com.xm.question.domain.model.QuestionDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class QuestionFetcher(
    private val questionClient: QuestionClient,
    private val questionStorage: QuestionStorage
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Result<List<QuestionDataModel>>> {
        return flow { emit(isCached()) }
            .onStart { Log.i(TAG, "Execution started") }
            .flatMapMerge { fetch(it) }
    }

    private fun isCached(): Boolean {
        val isCached = questionStorage.isCached()
        Log.d(TAG, "Questions storage: isCached: $isCached")
        return isCached
    }

    private fun fetch(isCached: Boolean): Flow<Result<List<QuestionDataModel>>> {
        return if (isCached) {
            Log.i(TAG, "Already fetched")
            flowOf(Result.success(questionStorage.get()))
        } else {
            Log.i(TAG, "Perform fetch")
            loadAndStore()
        }
    }

    private fun loadAndStore(): Flow<Result<List<QuestionDataModel>>> {
        return questionClient.load()
            .onEach { onLoaded(it) }
            .catch { e ->
                emit(Result.failure(ContractViolationException("Store questions failed", e)))
            }
    }

    private fun onLoaded(data: Result<List<QuestionDataModel>>) {
        val questions = data.getOrThrow()
        Log.d(TAG, "Perform to store questions: data:[$questions]")

        questionStorage.set(questions)
    }

    companion object {
        private val TAG = QuestionFetcher::class.java.simpleName
    }
}