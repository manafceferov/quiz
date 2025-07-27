package com.jafarov.quiz.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.Nationalized

@Entity
@Table(name = "quiz_topics")
open class Topic @JvmOverloads constructor(

    @Column(name = "name", nullable = false)
    @Nationalized
    open var name: String? = null,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean = false,

    @Column(name = "status")
    open var status: Boolean = false

): BaseEntity()