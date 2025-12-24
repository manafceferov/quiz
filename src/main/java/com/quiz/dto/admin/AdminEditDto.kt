package com.quiz.dto.admin

import com.quiz.dto.attachment.AttachmentViewDto

class AdminEditDto {
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var fatherName: String? = null
    var email: String? = null
    var password: String? = null
    var attachmentId: AttachmentViewDto? = null
}