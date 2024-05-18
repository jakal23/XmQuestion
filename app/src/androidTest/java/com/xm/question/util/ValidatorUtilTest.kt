package com.xm.question.util

import com.xm.question.domain.model.AnswerDataModel
import org.junit.Assert.*
import org.junit.Test

class ValidatorUtilTest {

    @Test
    fun testIsValidWithValidData() {
        val validModel = AnswerDataModel(id = 1, answer = "Valid Answer")
        assertTrue(ValidatorUtil.isValid(validModel))
    }

    @Test
    fun testIsValidWithInvalidId() {
        val invalidIdModel = AnswerDataModel(id = 0, answer = "Valid Answer")
        assertFalse(ValidatorUtil.isValid(invalidIdModel))
    }

    @Test
    fun testIsValidWithEmptyAnswer() {
        val emptyAnswerModel = AnswerDataModel(id = 1, answer = "")
        assertFalse(ValidatorUtil.isValid(emptyAnswerModel))
    }

    @Test
    fun testIsValidWithNegativeId() {
        val negativeIdModel = AnswerDataModel(id = -1, answer = "Valid Answer")
        assertFalse(ValidatorUtil.isValid(negativeIdModel))
    }

    @Test
    fun testIsValidWithInvalidIdAndEmptyAnswer() {
        val invalidModel = AnswerDataModel(id = 0, answer = "")
        assertFalse(ValidatorUtil.isValid(invalidModel))
    }
}