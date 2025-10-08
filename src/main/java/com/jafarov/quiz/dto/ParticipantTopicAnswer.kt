package com.jafarov.quiz.dto

open class ParticipantTopicAnswer @JvmOverloads constructor(

    var participantId: Long? = null,
    var topicId: Long? = null,
    var answers: List<ParticipantQuestionAnswer>? = null
) {
}