package com.jafarov.quiz.admin.dto.file

data class AttachmentUploadResponse(
    val fileName: String,
    val url: String,
    val message: String
)