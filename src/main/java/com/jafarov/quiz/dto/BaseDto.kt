package com.jafarov.quiz.dto

import com.jafarov.quiz.entity.User
import java.time.LocalDateTime

open class BaseDto (

    var id: Long? = null,

    open var createdById: Long? = null,

    open var createdBy: User? = null,

    open var lastModifiedById: Long? = null,

    open var lastModifiedBy: User? = null,

    open var createdAt: LocalDateTime? = null,

    open var lastModifiedDate: Long? = null,

    open var updateAt: LocalDateTime? = null
)