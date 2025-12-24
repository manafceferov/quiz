package com.quiz.dto.participant

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class ParticipantUpdateRequest(

    @field:NotBlank(message = "Ad boş ola bilməz")
    @field:Size(min = 2, max = 50, message = "Ad 2 ilə 50 arasında olmalıdır")
    var firstname: String,

    @field:NotBlank(message = "Soyad boş ola bilməz")
    @field:Size(min = 2, max = 50, message = "Soyad 2 ilə 50 arasında olmalıdır")
    var lastName: String,

    @field:NotBlank(message = "Email ünvanı boş ola bilməz")
    @field:Email(message = "Düzgün email ünvanı daxil edin")
    var email: String,

    var fatherName: String,
    var password: String,
    var confirmPassword: String,
    var gender: String,
    var phoneNumber: String,
    var birthDate: String,
)