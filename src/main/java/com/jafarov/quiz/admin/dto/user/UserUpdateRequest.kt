package com.jafarov.quiz.admin.dto.user

import com.jafarov.quiz.common.util.validation.FieldMatch
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import com.jafarov.quiz.admin.entity.Attachment
import org.springframework.web.multipart.MultipartFile

@FieldMatch(first = "password", second = "passwordConfirmation", message = "{FieldMatch}")
class UserUpdateRequest (

    @field:NotNull
    var id: Long? = null,

    @field:NotBlank
    @field:Size(min = 3, max = 100)
    var firstName: String? = null,

    @field:NotBlank
    @field:Size(min = 3, max = 100)
    var lastName: String? = null,

    @field:NotBlank
    @field:Size(min = 3, max = 100)
    var fatherName: String? = null,

    @field:NotBlank
    @field:Email
    @field:Size(min = 3, max = 100)
    var email: String? = null,

    var password: String? = null,
    var passwordConfirmation: String? = null,

    var file: MultipartFile? = null,

    // 👇 Bunu əlavə et
    var attachment: Attachment? = null
)