package com.jafarov.quiz.dto.topic

class TopicWithQuestionCountProjectionDto @JvmOverloads constructor(
    var id: Long? = null,
    var name: String? = null,
    var questionCount: Long? = null,
)