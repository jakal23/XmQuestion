package com.xm.question.ui.question.model

sealed interface QuestionAction {
    data object Back : QuestionAction
    data class PageChange(val option: IteratorOption) : QuestionAction
    data class SubmitAnswer(val answerItem: AnswerItem) : QuestionAction
}
