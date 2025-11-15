package com.jafarov.quiz.dto.profile

import com.jafarov.quiz.entity.Attachment
import org.springframework.web.multipart.MultipartFile

interface ProfileProjectionEdit {
    var id: Long?
    var firstName: String?
    var lastName: String?
    var fatherName: String?
    var email: String?
    var password: String?
    var confirmPassword: String?
    var phoneNumber: String?
    var birthDate: String?
    var gender: String?
    var file: MultipartFile?
    var attachmentId: Long?
    var attachmentUrl: String?
}