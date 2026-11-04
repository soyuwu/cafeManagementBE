package com.se114.cafeManagement.dto.category

import com.se114.cafeManagement.entity.Category
import java.time.LocalDateTime

data class CategoryResponse(
    val id: Long?,
    val name: String,
    val sortOrder: Int,
    val isActive: Boolean,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(category: Category) = CategoryResponse(
            id = category.id,
            name = category.name,
            sortOrder = category.sortOrder,
            isActive = category.isActive,
            createdAt = category.createdAt
        )
    }
}
