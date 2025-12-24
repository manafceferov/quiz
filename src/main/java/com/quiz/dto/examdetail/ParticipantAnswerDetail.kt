package com.quiz.dto.examdetail

import com.quiz.dto.BaseDto

class ParticipantAnswerDetail @JvmOverloads constructor(
    var answer: String? = null,
    var isCorrect: Boolean = false,
    var selected: Boolean = false
) : BaseDto()