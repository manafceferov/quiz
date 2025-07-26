package com.jafarov.quiz.dto.question

import com.jafarov.quiz.dto.answer.AnswerUpdateRequest
import com.jafarov.quiz.dto.BaseDto

open class QuestionUpdateRequest @JvmOverloads constructor (

    var question: String? = null,
    var topicId: Long? = null,
    var isActive: Boolean? = null,
    var answers: List<AnswerUpdateRequest> = mutableListOf()

): BaseDto(){
    fun setIsActive(value: Boolean?) {
        this.isActive = value
    }
}