package com.xm.question.ui.question.model


enum class IteratorOption {
    PREVIOUS,
    NEXT;

    companion object {

        fun findById(id: Int): IteratorOption? {
            return IteratorOption.entries
                .find { it.ordinal + 1 == id }
        }
    }
}