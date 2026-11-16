package com.se114.cafeManagement.repository

import com.se114.cafeManagement.entity.CafeTable
import org.springframework.data.jpa.repository.JpaRepository

interface TableRepository : JpaRepository<CafeTable, Long> {
    fun findByTableCode(tableCode: String): CafeTable?
}
