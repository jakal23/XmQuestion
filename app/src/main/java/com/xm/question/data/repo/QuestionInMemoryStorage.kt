package com.xm.question.data.repo

import com.xm.question.domain.data.QuestionStorage
import com.xm.question.domain.model.QuestionDataModel

class QuestionInMemoryStorage : QuestionStorage {

    private val inMemoryQuestions = mutableListOf<QuestionDataModel>()

    override fun get(): List<QuestionDataModel> {
        return inMemoryQuestions.sortedBy { it.id }
    }

    override fun set(questions: List<QuestionDataModel>?) {
        inMemoryQuestions.clear()
        questions?.let {
            inMemoryQuestions.addAll(questions)
        }
    }

    override fun isCached(): Boolean {
        return inMemoryQuestions.size > 0
    }


}