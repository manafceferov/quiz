package com.quiz.dto.exam

data class AnswerSubmitRequest(
    var questionId: Long,
    var answerId: Long
)