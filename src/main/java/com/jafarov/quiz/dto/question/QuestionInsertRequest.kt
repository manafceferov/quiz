package com.jafarov.quiz.dto.question

import com.jafarov.quiz.dto.BaseDto
import com.jafarov.quiz.dto.answer.AnswerInsertRequest

open class QuestionInsertRequest @JvmOverloads constructor(

    var question: String? = null,
    var topicId: Long? = null,
    var isActive: Boolean = false,
    var answers: List<AnswerInsertRequest>? = null,
    var byParticipant: Long? = null


) : BaseDto() {

}

