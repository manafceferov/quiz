package com.jafarov.quiz.admin.dto.core

open class BaseGuidDto @JvmOverloads constructor(

        open var id: String? = null,
        var deleted: Boolean? = null

) : BaseAuditDto()