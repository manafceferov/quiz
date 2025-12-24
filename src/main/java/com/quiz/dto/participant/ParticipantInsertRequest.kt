package com.quiz.dto.participant

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class ParticipantInsertRequest(

    @field:NotBlank(message = "İstifadəçi adı boş ola bilməz")
    @field:Size(min = 3, max = 50, message = "İstifadəçi adı 3-50 simvoldan ibarət olmalıdır")
    var firstname: String,

    @field:NotBlank(message = "Email ünvanı boş ola bilməz")
    @field:Email(message = "Düzgün email ünvanı daxil edin")
    var email: String,

    @field:NotBlank(message = "Şifrə boş ola bilməz")
    @field:Size(min = 8, message = "Şifrə ən azı 8 simvoldan ibarət olmalıdır")
    var password: String,

    @field:NotBlank(message = "Şifrəni təkrarlayın")
    var confirmPassword: String
)