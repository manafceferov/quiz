package com.jafarov.quiz.admin.dto.core

import java.time.LocalDateTime

open class BaseAuditDto {

    var createdBy: UserAuditorDto? = null
    var createdDate: LocalDateTime? = null
    var lastModifiedBy: UserAuditorDto? = null
    var lastModifiedDate: LocalDateTime? = null

}