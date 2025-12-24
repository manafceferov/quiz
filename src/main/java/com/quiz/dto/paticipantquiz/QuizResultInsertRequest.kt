package com.quiz.dto.paticipantquiz

import com.quiz.dto.BaseDto

class QuizResultInsertRequest @JvmOverloads constructor(
    var participantId: Long? = null,
    var topicId: Long? = null,
    var correctAnswersCount: Long? = null,
    var correctPercent: Long? = null,
    var answers: List<ParticipantAnswerInsertRequest> = mutableListOf(),
    var questionsCount: Long? = null
) : BaseDto()



