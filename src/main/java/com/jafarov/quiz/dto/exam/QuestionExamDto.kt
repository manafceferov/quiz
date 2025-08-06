package com.jafarov.quiz.dto.exam

data class QuestionExamDto(
    var id: Long? = null,
    var question: String? = null,
    var answers: List<AnswerExamDto> = emptyList()
)
