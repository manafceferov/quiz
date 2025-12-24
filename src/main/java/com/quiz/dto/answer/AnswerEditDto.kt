package com.quiz.dto.answer

import com.quiz.dto.BaseDto

open class AnswerEditDto @JvmOverloads constructor(

    var answer: String? = null,
    var isCorrect: Boolean? = false,
    var questionId: Long? = null,
    var isActive: Boolean = true

): BaseDto(){
    fun setIsCorrect(value: Boolean?) {
        this.isCorrect = value
    }
    fun setIsActive(value: Boolean) {
        this.isActive = value
    }
}
