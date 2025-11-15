package com.jafarov.quiz.dto.exam

data class AnswerExamDto(
    var id: Long? = null,
    var answer: String? = null,
    var isCorrect: Boolean = false
)
