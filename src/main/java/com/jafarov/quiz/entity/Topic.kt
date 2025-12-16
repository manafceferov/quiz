package com.jafarov.quiz.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Nationalized

@Entity
@Table(name = "topics")
open class Topic @JvmOverloads constructor(

    @Nationalized
    @Column(name = "name", nullable = false)
    open var name: String? = null,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean = false,

    @Column(name = "by_participant", nullable = false)
    open var byParticipant: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "by_participant", referencedColumnName = "id", insertable = false, updatable = false)
    open var participant: Participant? = null,

): BaseEntity()