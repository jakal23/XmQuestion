package com.xm.question.domain.usecase

import android.util.Log
import com.xm.question.domain.data.QuestionClient
import com.xm.question.domain.model.AnswerDataModel
import com.xm.question.util.ValidatorUtil.isValid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import java.security.InvalidParameterException

class SaveAnswer(
    private val questionClient: QuestionClient
) {

    @Throws(InvalidParameterException::class)
    operator fun invoke(answer: AnswerDataModel): Flow<Result<Unit>> {
        val isValid = isValid(answer)

        return when {
            isValid -> save(answer)

            else -> flowOf(
                Result.failure(
                    InvalidParameterException("User answer must be ont empty. answer: $answer")
                )
            )
        }
    }

    private fun save(answer: AnswerDataModel): Flow<Result<Unit>> {
        return questionClient.save(answer)
            .onStart {
                Log.d(TAG, "Preparing to save answer. data: $answer")
            }
    }

    companion object {
        private val TAG = SaveAnswer::class.java.simpleName
    }
}
