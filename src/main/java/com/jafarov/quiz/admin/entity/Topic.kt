package com.jafarov.quiz.admin.entity

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

    @Column(name = "status")
    open var status: Boolean = false

):BaseEntity()

