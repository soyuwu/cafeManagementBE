package com.se114.cafeManagement.repository

import com.se114.cafeManagement.entity.ProductVariant
import org.springframework.data.jpa.repository.JpaRepository

interface ProductVariantRepository : JpaRepository<ProductVariant, Long>
