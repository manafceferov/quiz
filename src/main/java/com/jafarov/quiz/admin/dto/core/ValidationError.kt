package com.jafarov.quiz.admin.dto.core

class ValidationError @JvmOverloads constructor(
        val path: String? = null,
        val field: String? = null,
        val messages: List<String>? = null
)