package com.quiz.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Nationalized
import org.hibernate.annotations.OptimisticLockType
import org.hibernate.annotations.OptimisticLocking

@Entity
@Table(name = "questions")
@DynamicUpdate
@OptimisticLocking(type = OptimisticLockType.ALL)
open class Question @JvmOverloads constructor(

    @Column(name = "question")
    @Nationalized
    open var question: String? = null,

    @Column(name = "is_active")
    open var isActive: Boolean? = false,

    @Column(name = "topic_id")
    open var topicId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", insertable = false, updatable = false)
    open var topic: Topic? = null,

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    open var answers: MutableSet<Answer>? = null,

    @Version
    @Column(name = "version", nullable = false)
    open var version: Int? = null,

    @Column(name = "by_participant", nullable = false)
    open var byParticipant: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "by_participant", referencedColumnName = "id", insertable = false, updatable = false)
    open var participant: Participant? = null,

) : BaseEntity()