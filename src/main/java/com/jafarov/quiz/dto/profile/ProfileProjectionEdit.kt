package com.jafarov.quiz.dto.profile

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
    var attachment: Long?
    var attachmentUrl: String?
}