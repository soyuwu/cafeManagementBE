package com.se114.cafeManagement.controller

import com.se114.cafeManagement.service.CategoryService
import com.se114.cafeManagement.service.ProductService
import com.se114.cafeManagement.service.TableService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/menu")
class MenuController(private val categoryService: CategoryService, private val productService: ProductService) {
    @GetMapping("/categories") fun categories() = categoryService.getActiveCategories()
    @GetMapping("/products") fun products() = productService.getAvailableProducts()
}

@RestController
@RequestMapping("/api/tables")
class AvailableTableController(private val tableService: TableService) {
    @GetMapping("/available") fun available() = tableService.getAvailableTables()
}
