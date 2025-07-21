package com.jafarov.quiz.admin.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Nationalized


@Entity
@Table(name = "answers")
open class Answer @JvmOverloads constructor(

    @Nationalized
    @Column(name = "answer", nullable = false)
    open var answer: String? = null,

    @Column(name = "is_correct", nullable = false)
    open var isCorrect: Boolean = false,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean = false,

    @Column(name = "question_id", nullable = false)
    open var questionId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", updatable = false, insertable = false)
    open var question: Question? = null

):BaseEntity()