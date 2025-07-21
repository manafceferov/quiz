package com.jafarov.quiz.admin.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Nationalized


@Table(name = "questions")
@Entity
open class Question @JvmOverloads constructor(

    @Column(name = "question", nullable = false)
    @Nationalized
    open var question: String? = null,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean = false,

    @Column(name = "topic_id")
    open var topicId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", insertable = false, updatable = false)
    open var topic: Topic? = null,

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    open var answers: MutableSet<Answer>? = null

):BaseEntity()