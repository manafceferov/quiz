package com.jafarov.quiz.admin.dto.question

import com.jafarov.quiz.admin.dto.answer.AnswerUpdateRequest
import com.jafarov.quiz.admin.dto.core.BaseDto

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