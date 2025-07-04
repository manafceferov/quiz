package com.jafarov.quiz.admin.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Nationalized
import java.time.LocalDateTime

@Entity
@Table(name = "users")
open class User @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(nullable = false, unique = true, length = 100)
    var email: String? = null,

    @Nationalized
    @Column(name = "last_name", length = 100)
    var lastName: String? = null,

    @Nationalized
    @Column(name = "first_name", length = 100)
    var firstName: String? = null,

    @Column(nullable = false, length = 100)
    var password: String? = null,

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    var createdAt: LocalDateTime? = null,

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    var updatedAt: LocalDateTime? = null,

    @Column(name = "status", nullable = false)
    var status: Boolean? = true

) {
}