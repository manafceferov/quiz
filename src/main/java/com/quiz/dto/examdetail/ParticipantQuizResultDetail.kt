package com.quiz.dto.examdetail

import com.quiz.dto.BaseDto

class ParticipantQuizResultDetail @JvmOverloads constructor(
    var participantId: Long? = null,
    var topicName: String? = null,
    var correctAnswersCount: Long? = null,
    var correctPercent: Long? = null,
    var questionCount: Long? = null,
    var questions: List<ParticipantQuestionWithAnswers> = mutableListOf()
) : BaseDto()