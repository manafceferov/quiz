package com.jafarov.quiz.admin.dto.core

import javax.validation.constraints.NotNull

class SourceSequenceDto(

        @field:NotNull
        var sourceId: String? = null,

        var sequence: Int? = null

)