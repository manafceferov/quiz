package com.jafarov.quiz.admin.dto.question

import com.jafarov.quiz.admin.dto.core.BaseDto
import com.jafarov.quiz.admin.dto.answer.AnswerInsertRequest

open class QuestionInsertRequest @JvmOverloads constructor(

    var question: String? = null,
    var topicId: Long? = null,
    var isActive: Boolean? = false,
    var answers: List<AnswerInsertRequest>? = null

) : BaseDto() {
    fun setIsActive(value: Boolean?) {
        this.isActive = value
    }
}

