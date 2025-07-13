package com.jafarov.quiz.admin.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Nationalized

@Entity
@Table(name = "users")
class User @JvmOverloads constructor(

    @Nationalized
    @Column(name = "first_name", nullable = false, length = 100)
    var firstName: String = "",

    @Nationalized
    @Column(name = "last_name", nullable = false, length = 100)
    var lastName: String = "",

    @Nationalized
    @Column(name = "father_name", nullable = false, length = 100)
    var fatherName: String = "",

    @Column(name = "email", nullable = false, unique = true, length = 100)
    var email: String = "",

    @Column(name = "password", nullable = false, length = 100)
    var password: String = "",

    @Column(name = "status", nullable = false)
    var status: Boolean = true,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var attachment: Attachment? = null
):BaseEntity()
