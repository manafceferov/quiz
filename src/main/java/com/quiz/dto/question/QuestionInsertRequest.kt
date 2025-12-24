package com.quiz.dto.question

import com.quiz.dto.BaseDto
import com.quiz.dto.answer.AnswerInsertRequest

open class QuestionInsertRequest @JvmOverloads constructor(

    var question: String? = null,
    var topicId: Long? = null,
    var isActive: Boolean = false,
    var answers: List<AnswerInsertRequest>? = null,
    var byParticipant: Long? = null


) : BaseDto() {

}

