package com.quiz.dto.participant

import com.quiz.dto.BaseDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class ParticipantEditDto(

    @field:NotBlank(message = "Ad boş ola bilməz")
    @field:Size(min = 2, max = 50, message = "Ad 2 ilə 50 arasında olmalıdır")
    var firstName: String,

    @field:NotBlank(message = "Soyad boş ola bilməz")
    @field:Size(min = 2, max = 50, message = "Soyad 2 ilə 50 arasında olmalıdır")
    var lastName: String,

    @field:NotBlank(message = "Email ola bilməz")
    @field:Size(min = 5, max = 100, message = "Email 5 ilə 100 arasında olmalıdır")
    var email: String,

    var fatherName: String,
    var password: String,
    var confirmPassword: String,
    var gender: String,
    var phoneNumber: String,
    var birthDate: String,
    val attachmentUrl: String? = null
): BaseDto()