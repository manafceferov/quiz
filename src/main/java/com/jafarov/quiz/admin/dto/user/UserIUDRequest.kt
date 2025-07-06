package com.jafarov.quiz.admin.dto.user

import com.jafarov.quiz.common.util.validation.FieldMatch
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import org.springframework.web.multipart.MultipartFile

@FieldMatch(first = "password", second = "passwordConfirmation", message = "{FieldMatch}")
class UserIUDRequest @JvmOverloads constructor(
    var id: Long? = null,

    @field: NotNull()
    @field: Size(min = 3, max = 100 , message = "{firstName} {Size}")
    @field: NotBlank(message = "{firstName} {NotBlank}")
    var firstName: String? = null,

    @field: NotNull
    @field: Size(min = 3, max = 100 , message = "{lastName} {Size}")
    @field: NotBlank(message = "{lastName} {NotBlank}")
    var lastName: String? = null,

    @field: NotNull
    @field: Size(min = 3, max = 100 , message = "{fatherName} {Size}")
    @field: NotBlank(message = "{fatherName} {NotBlank}")
    var fatherName: String? = null,

    @field: NotNull
    @field: Email
    @field: Size(min = 3, max = 100 , message = "{email} {Size}")
    @field: NotBlank(message = "{email} {NotBlank}")
    var email: String? = null,

    @field: NotNull
    @field: Size(min = 3, max = 100 , message = "{password} {Size}")
    @field: NotBlank(message = "{password} {NotBlank}")
    var password: String? = null,

    @field: NotNull
    @field: Size(min = 3, max = 100 , message = "{passwordConfirmation} {Size}")
    @field: NotBlank(message = "{passwordConfirmation} {NotBlank}")
    var passwordConfirmation: String? = null,

    @field: NotNull
    var file: MultipartFile? = null
) {
}