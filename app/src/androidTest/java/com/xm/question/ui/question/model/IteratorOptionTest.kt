package com.xm.question.ui.question.model

import org.junit.Assert.*
import org.junit.Test

class IteratorOptionTest {

    @Test
    fun testFindById() {
        assertEquals(IteratorOption.PREVIOUS, IteratorOption.findById(1))
        assertEquals(IteratorOption.NEXT, IteratorOption.findById(2))
        assertNull(IteratorOption.findById(0))
        assertNull(IteratorOption.findById(3))
    }

    @Test
    fun testEnumValues() {
        assertArrayEquals(arrayOf(IteratorOption.PREVIOUS, IteratorOption.NEXT), IteratorOption.values())
    }
}