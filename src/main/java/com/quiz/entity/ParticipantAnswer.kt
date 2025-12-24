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

@Entity
@Table(name = "participant_answers")
open class ParticipantAnswer @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    open var id: Long? = null,

    @Column(name = "quiz_result_id")
    open var quizResultId: Long? = null,

    @Column(name = "question_id")
    open var questionId: Long? = null,

    @Column(name = "answer_id")
    open var answerId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_result_id",insertable = false, updatable = false)
    open var quizResult: QuizResult? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id",insertable = false, updatable = false)
    open var question: Question? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", insertable = false, updatable = false)
    open var answer: Answer? = null
)