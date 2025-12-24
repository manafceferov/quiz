package com.quiz.dto.topic

interface TopicWithQuestionCountProjection {

    var id: Long?
    var name: String?
    var questionCount: Long?
    var description: String?
}