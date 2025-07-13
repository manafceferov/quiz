package com.jafarov.quiz.admin.dto.core

class ApiResponse<T> @JvmOverloads constructor(
        val data: T? = null,
        val code: String? = null,
        val message: String? = null,
        val metadata: String? = null
)