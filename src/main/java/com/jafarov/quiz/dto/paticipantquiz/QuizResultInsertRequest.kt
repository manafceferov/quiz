package com.jafarov.quiz.dto.paticipantquiz

import com.jafarov.quiz.dto.BaseDto

class QuizResultInsertRequest @JvmOverloads constructor(
    var participantId: Long? = null,
    var topicId: Long? = null,
    var correctAnswersCount: Long? = null,
    var correctPercent: Long? = null,
    var answers: List<ParticipantAnswerInsertRequest> = mutableListOf(),
    var questionCount: Long? = null,
) : BaseDto() {
    fun calculateQuestionCount() {
        this.questionCount = answers.size.toLong()
    }
}
