package com.jafarov.quiz.admin.dto.answer

import com.jafarov.quiz.admin.dto.core.BaseDto

open class AnswerInsertRequest @JvmOverloads constructor(

    var answer: String? = null,
    var isCorrect: Boolean = false,
    var questionId: Long? = null,
    var isActive: Boolean = true

) : BaseDto()