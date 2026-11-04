package com.se114.cafeManagement.service

import com.se114.cafeManagement.dto.category.CategoryResponse
import com.se114.cafeManagement.dto.category.CreateCategoryRequest
import com.se114.cafeManagement.entity.Category
import com.se114.cafeManagement.repository.CategoryRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    @Transactional
    fun createCategory(request: CreateCategoryRequest): CategoryResponse {
        val name = request.name.trim()
        if (categoryRepository.existsByName(name)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists: $name")
        }

        val category = Category(
            name = name,
            sortOrder = request.sortOrder,
            isActive = request.isActive
        )

        return CategoryResponse.from(categoryRepository.save(category))
    }

    @Transactional(readOnly = true)
    fun getAllCategories(): List<CategoryResponse> =
        categoryRepository.findAllByOrderBySortOrderAsc().map(CategoryResponse::from)
}
