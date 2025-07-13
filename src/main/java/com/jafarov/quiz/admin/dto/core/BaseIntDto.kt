package com.jafarov.quiz.admin.dto.core

open class BaseIntDto @JvmOverloads constructor(
        open var id: Int? = null
): IsDeleted()