package com.quiz.dto.participant

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class ParticipantRegisterDto (

    @field: NotNull
    @field: Email
    @field: Size(min = 3, max = 100 , message = "{email} {Size}")
    @field: NotBlank(message = "Email boş ola bilməz")
    var email: String? = null,

    @field: NotNull
    @field: Size(min = 3, max = 100 , message = "{password} {Size}")
    @field: NotBlank(message = "Şifrə boş ola bilməz")
    var password: String? = null,

    @field: NotNull
    @field: Size(min = 3, max = 100 , message = "{passwordConfirmation} {Size}")
    @field: NotBlank(message = "Şifrə təsdiqi boş ola bilməz")
    @field: Nullable
    var passwordConfirmation: String? = null,
)