package com.se114.cafeManagement.dto.user

import com.se114.cafeManagement.entity.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank
    val fullName: String,

    @field:NotBlank
    val phone: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String,

    val role: UserRole = UserRole.CUSTOMER
)