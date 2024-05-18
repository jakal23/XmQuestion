package com.xm.question.hilt

import com.xm.question.data.repo.QuestionClientImpl
import com.xm.question.domain.data.QuestionClient
import com.xm.question.domain.data.QuestionStorage
import com.xm.question.domain.source.QuestionService
import com.xm.question.domain.usecase.QuestionFetcher
import com.xm.question.domain.usecase.SaveAnswer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object QuestionModule {

    @Provides
    @ViewModelScoped
    fun saveAnswer(
        questionClient: QuestionClient
    ): SaveAnswer {
        return SaveAnswer(questionClient)
    }

    @Provides
    @ViewModelScoped
    fun questionFetcher(
        questionClient: QuestionClient,
        questionStorage: QuestionStorage,
    ): QuestionFetcher {
        return QuestionFetcher(questionClient, questionStorage)
    }

    @Provides
    @ViewModelScoped
    fun questionClient(
        service: QuestionService
    ): QuestionClient =
        QuestionClientImpl(service)

    @Provides
    @ViewModelScoped
    fun service(retrofit: Retrofit): QuestionService =
        retrofit.create(QuestionService::class.java)
}

