package com.jafarov.quiz.admin.dto.core

import javax.validation.Valid
import javax.validation.constraints.NotNull

class ApproveRequest @JvmOverloads constructor(

        @field: Valid
        @field: NotNull
        var sequenceList: List<SourceSequenceDto>? = null,

        var comment: String? = null,
        var rejectReasonId: String? = null,
        var approveReasonId: Long? = null

)