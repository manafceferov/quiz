package com.jafarov.quiz.admin.dto.file

data class FileUploadResponse(
    val fileName: String,
    val url: String,
    val message: String
)