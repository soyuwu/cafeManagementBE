package com.se114.cafeManagement.repository

import com.se114.cafeManagement.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun existsByName(name: String): Boolean
    fun findAllByOrderBySortOrderAsc(): List<Category>
}
