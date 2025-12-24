package com.quiz.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Nationalized


//@Entity
//@Table(name = "SourceTypes", schema = "Register")
open class SourceType @JvmOverloads constructor(

    @Id
    @Column(name = "Id", nullable = false)
    open var id: String? = null,

    @Nationalized
    @Column(name = "Description", length = 2000)
    open var description: String? = null

)