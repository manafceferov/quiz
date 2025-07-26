package com.jafarov.quiz.dto.topic

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

open class TopicUpdateRequest @JvmOverloads constructor(

    var id: Long,

    @field:NotBlank
    @field:Size(max = 255)
    var name: String,
)






