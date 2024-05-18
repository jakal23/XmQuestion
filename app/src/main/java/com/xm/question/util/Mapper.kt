package com.xm.question.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import com.xm.question.domain.model.AnswerDataModel
import com.xm.question.domain.model.QuestionDataModel
import com.xm.question.ui.navigation.OptionItem
import com.xm.question.ui.question.model.AnswerItem
import com.xm.question.ui.question.model.IteratorOption
import com.xm.question.ui.question.model.QuestionItem

object Mapper {

    @OptIn(ExperimentalFoundationApi::class)
    fun PagerState.asTitle(): String {
        return "Question ${currentPage + 1}/${pageCount}"
    }

    fun IteratorOption.toOptionItem(enabled: Boolean): OptionItem {
        return OptionItem(ordinal + 1, enabled, name.lowercase())
    }

    fun QuestionDataModel.toQuestionItem(answer: String = ""): QuestionItem {
        return QuestionItem(id, question, answer)
    }

    fun AnswerItem.toDataModel(): AnswerDataModel {
        return AnswerDataModel(questionId, answer)
    }
}