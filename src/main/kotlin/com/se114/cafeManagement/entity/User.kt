package com.se114.cafeManagement.entity

import jakarta.persistence.*
import java.time.LocalDateTime

enum class UserRole{
    CUSTOMER, ADMIN, STAFF
}

@Entity
@Table(name = "users")
class User(
    @Column(name = "full_name", nullable = false, length = 255)
    var fullName: String,

    @Column(nullable = false, unique = true, length = 20)
    var phone: String,

    @Column(nullable = false, unique = true, length = 255)
    var email: String,

    @Column(name = "password_hash", nullable = false, length = 255)
    var passwordHash: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var role: UserRole = UserRole.CUSTOMER,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}