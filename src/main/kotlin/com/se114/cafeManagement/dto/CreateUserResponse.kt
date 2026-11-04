package com.se114.cafeManagement.dto.user

import com.se114.cafeManagement.entity.User
import com.se114.cafeManagement.entity.UserRole
import java.time.LocalDateTime

data class UserResponse(
    val id: Long?,
    val fullName: String,
    val phone: String,
    val email: String,
    val role: UserRole,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(user: User) = UserResponse(
            id = user.id,
            fullName = user.fullName,
            phone = user.phone,
            email = user.email,
            role = user.role,
            isActive = user.isActive,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }
}