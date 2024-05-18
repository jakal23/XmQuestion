package com.xm.question.data.repo

import android.util.Log
import com.xm.question.domain.data.QuestionClient
import com.xm.question.domain.model.AnswerDataModel
import com.xm.question.domain.model.QuestionDataModel
import com.xm.question.domain.source.QuestionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class QuestionClientImpl(
    private val service: QuestionService
) : QuestionClient {

    override fun load(): Flow<Result<List<QuestionDataModel>>> {
        return flow { emit(catchingLoad()) }
            .onEach { Log.d(TAG, "Question load request. Result: $it") }
            .flowOn(Dispatchers.IO)
    }

    override fun save(answer: AnswerDataModel): Flow<Result<Unit>> {
        return flow { emit(catchingSubmit(answer)) }
            .onEach { Log.d(TAG, "Submit answer request. Result: $it") }
            .flowOn(Dispatchers.IO)
    }

    private suspend fun catchingLoad(): Result<List<QuestionDataModel>> {
        return kotlin.runCatching { service.load() }
    }

    private suspend fun catchingSubmit(answer: AnswerDataModel): Result<Unit> {
        return kotlin.runCatching { service.submitAnswer(answer) }
    }

    companion object {
        private const val TAG = "QuestionClient"
    }
}