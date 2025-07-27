package com.jafarov.quiz.entity

import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.OneToOne

@MappedSuperclass
open class AttachmentRelatedEntity @JvmOverloads constructor(

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
    open var user: User? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
    open var participant: Participant? = null,

    ) : BaseEntity()