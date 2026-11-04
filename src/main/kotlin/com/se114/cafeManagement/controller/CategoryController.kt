package com.se114.cafeManagement.controller

import com.se114.cafeManagement.dto.category.CategoryResponse
import com.se114.cafeManagement.dto.category.CreateCategoryRequest
import com.se114.cafeManagement.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(@Valid @RequestBody request: CreateCategoryRequest): CategoryResponse =
        categoryService.createCategory(request)

    @GetMapping
    fun getAllCategories(): List<CategoryResponse> = categoryService.getAllCategories()
}
