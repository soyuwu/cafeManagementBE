package com.se114.cafeManagement.controller

import com.se114.cafeManagement.dto.product.CreateProductRequest
import com.se114.cafeManagement.dto.product.ProductResponse
import com.se114.cafeManagement.dto.product.UpdateProductAvailabilityRequest
import com.se114.cafeManagement.dto.product.UpdateProductRequest
import com.se114.cafeManagement.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PatchMapping

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@Valid @RequestBody request: CreateProductRequest): ProductResponse =
        productService.createProduct(request)

    @GetMapping
    fun getAllProducts(): List<ProductResponse> = productService.getAllProducts()

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @Valid @RequestBody request: UpdateProductRequest): ProductResponse = productService.updateProduct(id, request)

    @PatchMapping("/{id}/availability")
    fun updateAvailability(@PathVariable id: Long, @Valid @RequestBody request: UpdateProductAvailabilityRequest): ProductResponse = productService.updateAvailability(id, request)
}
