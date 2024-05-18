package com.xm.question.domain.usecase

import com.google.common.truth.Truth
import com.xm.question.data.repo.QuestionClientImpl
import com.xm.question.domain.model.AnswerDataModel
import com.xm.question.domain.source.QuestionService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.internal.createInstance
import org.mockito.kotlin.stub
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.security.InvalidParameterException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29], manifest = Config.NONE)
class SaveAnswerTest {

    private lateinit var testableSaveAnswerUseCase: SaveAnswer
    private val service = Mockito.mock(QuestionService::class.java)

    @Before
    fun setUp() {
        val questionClient = QuestionClientImpl(service)
        testableSaveAnswerUseCase = SaveAnswer(questionClient)
    }

    @Test
    fun `save answer then verify result is success`() = runTest {
        service.stub {
            onBlocking { submitAnswer(Mockito.any() ?: createInstance()) }
                .thenAnswer { }
        }

        val body = AnswerDataModel(1, "Some answer")
        val res = testableSaveAnswerUseCase.invoke(body).first()

        Truth.assertThat(res.isSuccess).isTrue()
    }

    @Test
    fun `save invalid answer then verify result is success`() = runTest {
        val body = AnswerDataModel(-1, "Some answer")

        val res = testableSaveAnswerUseCase.invoke(body).first()

        Truth.assertThat(res.isFailure).isTrue()
        Truth.assertThat(res.exceptionOrNull()).isInstanceOf(InvalidParameterException::class.java)

    }
}