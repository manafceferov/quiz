package com.jafarov.quiz.dto.question

import com.jafarov.quiz.dto.answer.AnswerUpdateRequest
import com.jafarov.quiz.dto.BaseDto

open class QuestionUpdateRequest @JvmOverloads constructor (

    var question: String? = null,
    var topicId: Long? = null,
    var answers: List<AnswerUpdateRequest> = mutableListOf(),
    var byParticipant: Long? = null


): BaseDto()