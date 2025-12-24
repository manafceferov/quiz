package com.quiz.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "quiz_results")
open class QuizResult @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    open var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", insertable = false, updatable = false)
    open var participant: Participant? = null,

    @Column(name = "participant_id")
    open var participantId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", insertable = false, updatable = false)
    open var topic: Topic? = null,

    @Column(name = "topic_id")
    open var topicId: Long? = null,

    @Column(name = "created_at", nullable = false)
    open var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "questions_count", nullable = false)
    open var questionsCount: Long? = null,

    @Column(name = "correct_answers_count", nullable = false)
    open var correctAnswersCount: Long? = null,

    @Column(name = "correct_percent", nullable = false)
    open var correctPercent: Long? = null
)
