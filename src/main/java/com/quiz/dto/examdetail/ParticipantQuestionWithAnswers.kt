package com.quiz.dto.examdetail

import com.quiz.dto.BaseDto

class ParticipantQuestionWithAnswers @JvmOverloads constructor(
    var question: String? = null,
    var answers: List<ParticipantAnswerDetail> = mutableListOf()
) : BaseDto()