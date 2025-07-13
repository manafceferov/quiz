package com.jafarov.quiz.admin.dto.core

open class BaseIUDRequest @JvmOverloads constructor(
        open var id: String? = null,
) : IsDeleted()