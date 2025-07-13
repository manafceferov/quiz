package com.jafarov.quiz.admin.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Nationalized


@Table(name = "questions")
@Entity
open class Question @JvmOverloads constructor(

    @Column(name = "question", nullable = false)
    @Nationalized
    open var question: String? = null,

    @Column(name = "topic_id")  // YAZAN hissə
    open var topicId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", insertable = false, updatable = false)  // OXUYAN hissə
    open var topic: Topic? = null,

):BaseEntity()