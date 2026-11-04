package com.se114.cafeManagement.dto.product

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class CreateProductRequest(
    @field:NotNull
    @field:Positive
    val categoryId: Long,

    @field:NotBlank
    val name: String,

    val description: String? = null,
    val imageUrl: String? = null,
    val isAvailable: Boolean = true,
    val isActive: Boolean = true
)
