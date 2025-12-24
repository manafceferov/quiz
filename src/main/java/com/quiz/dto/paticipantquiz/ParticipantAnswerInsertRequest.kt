package com.quiz.dto.paticipantquiz

import com.quiz.dto.BaseDto

class ParticipantAnswerInsertRequest @JvmOverloads constructor(
    var questionId: Long? = null,
    var answerId: Long? = null,
    var quizResultId: Long? = null
) : BaseDto()
