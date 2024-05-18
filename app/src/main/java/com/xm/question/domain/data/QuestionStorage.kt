package com.xm.question.domain.data

import com.xm.question.domain.model.QuestionDataModel

interface QuestionStorage {

    fun get(): List<QuestionDataModel>

    fun set(questions: List<QuestionDataModel>?)

    fun isCached(): Boolean
}