package com.jafarov.quiz.entity

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Nationalized

@Entity
@Table(name = "users")
@DiscriminatorValue("USER")
open class User @JvmOverloads constructor(

    @Nationalized
    @Column(name = "first_name", nullable = false, length = 100)
    open var firstName: String? = null,

    @Nationalized
    @Column(name = "last_name", nullable = false, length = 100)
    open var lastName: String? = null,

    @Nationalized
    @Column(name = "father_name", nullable = false, length = 100)
    open var fatherName: String? = null,

    @Column(name = "email", nullable = false, unique = true, length = 100)
    open var email: String? = null,

    @Column(name = "password", nullable = false, length = 100)
    open var password: String? = null,

    @Column(name = "status", nullable = false)
    open var status: Boolean = true,

    @Column(name = "role", nullable = false)
    open var role: String = "ROLE_ADMIN",

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    open var attachment: Attachment? = null
): BaseEntity()