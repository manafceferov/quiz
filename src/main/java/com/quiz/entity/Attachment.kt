package com.quiz.entity

import com.quiz.enums.OwnerType
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.DiscriminatorType
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table

@Entity
@Table(name = "attachments")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "table_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ATTACHMENT")
open class Attachment @JvmOverloads constructor(

    @Column(name = "file_name")
    open var fileName: String? = null,

    @Column(name = "file_url")
    open var fileUrl: String? = null,

    @Column(name = "owner_id")
    open var ownerId: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type")
    open var ownerType: OwnerType? = null

    ) : AttachmentRelatedEntity()