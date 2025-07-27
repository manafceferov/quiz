package com.jafarov.quiz.dto.topic

interface TopicWithQuestionCountProjection {

    val id: Long?
    val name: String?
    val questionCount: Int?
    val description: String?
}