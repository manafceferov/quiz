package com.jafarov.quiz.admin.entity

import jakarta.persistence.*

@MappedSuperclass
open class AttachmentRelatedEntity @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    open var user: User? = null
)
