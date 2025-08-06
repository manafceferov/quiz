package com.jafarov.quiz.dto.topic

interface TopicWithQuestionCountProjection {

    var id: Long?
    var name: String?
    var questionCount: Int?
    var description: String?
}