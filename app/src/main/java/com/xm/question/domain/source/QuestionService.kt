package com.xm.question.domain.source

import com.xm.question.domain.model.AnswerDataModel
import com.xm.question.domain.model.QuestionDataModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QuestionService {

    @GET("questions")
    suspend fun load(): List<QuestionDataModel>

    @POST("question/submit")
    suspend fun submitAnswer(@Body answer: AnswerDataModel)

}
