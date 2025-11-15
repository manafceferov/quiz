package com.jafarov.quiz.dto.examdetail

import com.jafarov.quiz.dto.BaseDto

class ParticipantQuestionWithAnswers @JvmOverloads constructor(
    var question: String? = null,
    var answers: List<ParticipantAnswerDetail> = mutableListOf()
) : BaseDto()