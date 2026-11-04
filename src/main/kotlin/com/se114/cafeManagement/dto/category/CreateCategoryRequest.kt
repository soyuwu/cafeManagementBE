package com.se114.cafeManagement.dto.category

import jakarta.validation.constraints.NotBlank

data class CreateCategoryRequest(
    @field:NotBlank
    val name: String,

    val sortOrder: Int = 0,
    val isActive: Boolean = true
)
