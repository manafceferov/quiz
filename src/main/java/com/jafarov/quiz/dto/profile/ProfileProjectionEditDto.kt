package com.jafarov.quiz.dto.profile

import com.jafarov.quiz.entity.Attachment
import org.springframework.web.multipart.MultipartFile

class ProfileProjectionEditDto @JvmOverloads constructor(
    var id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var fatherName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var confirmPassword: String? = null,
    var phoneNumber: String? = null,
    var birthDate: String? = null,
    var gender: String? = null,
    var file: MultipartFile?  = null,
    var attachmentId: Long? = null,
    var attachmentUrl: String? = null
)
