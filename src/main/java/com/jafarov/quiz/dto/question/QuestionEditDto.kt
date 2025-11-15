package com.jafarov.quiz.dto.question

import com.jafarov.quiz.dto.answer.AnswerEditDto
import com.jafarov.quiz.dto.BaseDto

open class QuestionEditDto @JvmOverloads constructor (

    var question: String? = null,
    var topicId: Long? = null,
    var isActive: Boolean? = null,
    var answers: List<AnswerEditDto> = listOf(),
    var byParticipant: Long? = null

): BaseDto(){
    fun setIsActive(value: Boolean) {
        this.isActive = value
    }
}