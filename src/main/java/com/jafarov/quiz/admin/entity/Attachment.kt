package com.jafarov.quiz.admin.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "attachments")
class Attachment(

    @Column(name = "file_name", nullable = false, length = 255)
    var fileName: String = "",

    @Column(name = "file_url", nullable = false, length = 255)
    var fileUrl: String = "",

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

) : AttachmentRelatedEntity()