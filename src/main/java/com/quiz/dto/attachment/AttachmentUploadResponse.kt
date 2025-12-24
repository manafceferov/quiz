package com.quiz.dto.attachment

data class AttachmentUploadResponse(
    val fileName: String,
    val url: String,
    val message: String
)