package com.jafarov.quiz.dto.user

import com.jafarov.quiz.dto.attachment.AttachmentViewDto

class UserEditDto {
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var fatherName: String? = null
    var email: String? = null
    var password: String? = null
    var attachmentId: AttachmentViewDto? = null
}