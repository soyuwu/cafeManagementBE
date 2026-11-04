package com.se114.cafeManagement.service

import com.se114.cafeManagement.dto.product.CreateProductRequest
import com.se114.cafeManagement.dto.product.ProductResponse
import com.se114.cafeManagement.entity.Product
import com.se114.cafeManagement.repository.CategoryRepository
import com.se114.cafeManagement.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) {
    @Transactional
    fun createProduct(request: CreateProductRequest): ProductResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: ${request.categoryId}")
            }

        val product = Product(
            category = category,
            name = request.name.trim(),
            description = request.description,
            imageUrl = request.imageUrl,
            isAvailable = request.isAvailable,
            isActive = request.isActive
        )

        return ProductResponse.from(productRepository.save(product))
    }

    @Transactional(readOnly = true)
    fun getAllProducts(): List<ProductResponse> =
        productRepository.findAll().map(ProductResponse::from)
}
