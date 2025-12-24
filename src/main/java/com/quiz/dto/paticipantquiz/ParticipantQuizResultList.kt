package com.quiz.dto.paticipantquiz

import com.quiz.dto.BaseDto

class ParticipantQuizResultList @JvmOverloads constructor(
    var participantId: Long? = null,
    var topicName: String? = null,
    var correctAnswersCount: Long? = null,
    var correctPercent: Long? = null,
    var questionCount: Long? = null,
    var answers: List<ParticipantAnswerInsertRequest> = mutableListOf() // cavablar
) : BaseDto() {}
