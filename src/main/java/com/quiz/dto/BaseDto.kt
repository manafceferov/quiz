package com.quiz.dto

import com.quiz.entity.Admin
import java.time.LocalDateTime

open class BaseDto (

    var id: Long? = null,

    open var createdById: Long? = null,

    open var createdBy: Admin? = null,

    open var lastModifiedById: Long? = null,

    open var lastModifiedBy: Admin? = null,

    open var createdAt: LocalDateTime? = null,

    open var lastModifiedDate: Long? = null,

    open var updateAt: LocalDateTime? = null
)