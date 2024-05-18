package com.xm.question.hilt

import com.xm.question.data.repo.QuestionInMemoryStorage
import com.xm.question.domain.data.QuestionStorage
import com.xm.question.util.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun client(): Retrofit {
        return NetworkClient.xmClient()
    }

    @Provides
    @Singleton
    fun questionStorage(): QuestionStorage {
        return QuestionInMemoryStorage()
    }
}