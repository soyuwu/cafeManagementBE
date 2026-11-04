package com.se114.cafeManagement.dto.product

import com.se114.cafeManagement.entity.Product
import java.time.LocalDateTime

data class ProductResponse(
    val id: Long?,
    val categoryId: Long?,
    val name: String,
    val description: String?,
    val imageUrl: String?,
    val isAvailable: Boolean,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(product: Product) = ProductResponse(
            id = product.id,
            categoryId = product.category.id,
            name = product.name,
            description = product.description,
            imageUrl = product.imageUrl,
            isAvailable = product.isAvailable,
            isActive = product.isActive,
            createdAt = product.createdAt,
            updatedAt = product.updatedAt
        )
    }
}
