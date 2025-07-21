package com.jafarov.quiz.admin.dto.question

import com.jafarov.quiz.admin.dto.answer.AnswerEditDto
import com.jafarov.quiz.admin.dto.core.BaseDto

open class QuestionEditDto @JvmOverloads constructor (

    var question: String? = null,
    var topicId: Long? = null,
    var isActive: Boolean? = null,
    var answers: List<AnswerEditDto> = listOf()
): BaseDto(){
    fun setIsActive(value: Boolean) {
        this.isActive = value
    }
}