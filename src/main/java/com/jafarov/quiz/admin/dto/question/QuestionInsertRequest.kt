package com.jafarov.quiz.admin.dto.question

import com.jafarov.quiz.admin.dto.core.BaseDto

open class QuestionInsertRequest @JvmOverloads constructor(

    var question: String? = null,

    var topicId: Long? = null,
) : BaseDto()
