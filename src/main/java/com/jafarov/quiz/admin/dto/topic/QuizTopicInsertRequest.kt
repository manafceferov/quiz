package com.jafarov.quiz.admin.dto.topic

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

open class QuizTopicInsertRequest(

    @field:NotBlank(message = "Mövzu adı boş ola bilməz")
    @field:Size(max = 255)
    var name: String? = null,

    var createdAt: LocalDateTime? = null


)