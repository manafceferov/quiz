package com.jafarov.quiz.dto.topic

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

open class TopicInsertRequest(

    @field:NotBlank(message = "Mövzu adı boş ola bilməz")
    @field:Size(max = 255)
    var name: String? = null,

    var createdAt: LocalDateTime? = null


)