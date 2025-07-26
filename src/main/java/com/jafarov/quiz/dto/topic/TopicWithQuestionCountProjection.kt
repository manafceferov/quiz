package com.jafarov.quiz.dto.topic

interface TopicWithQuestionCountProjection {

    val id: Long?
    val name: String?
    val count: Int?
    val description: String?
}