package com.xm.question.util

import com.xm.question.domain.model.AnswerDataModel

object ValidatorUtil {

    fun isValid(answerDataModel: AnswerDataModel): Boolean {
        return answerDataModel.id > 0 &&
                answerDataModel.answer.isNotEmpty()
    }
}