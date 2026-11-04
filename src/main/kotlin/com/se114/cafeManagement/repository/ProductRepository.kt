package com.se114.cafeManagement.repository

import com.se114.cafeManagement.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>
