package com.jafarov.quiz.admin.dto.answer

import com.jafarov.quiz.admin.dto.core.BaseDto

open class AnswerUpdateRequest @JvmOverloads constructor(

    var answer: String? = null,
    var isCorrect: Boolean = false,
    var questionId: Long? = null

) : BaseDto()