package com.xm.question.domain.data

import com.xm.question.domain.model.AnswerDataModel
import com.xm.question.domain.model.QuestionDataModel
import kotlinx.coroutines.flow.Flow

interface QuestionClient {

    fun load(): Flow<Result<List<QuestionDataModel>>>

    fun save(answer: AnswerDataModel): Flow<Result<Unit>>
}