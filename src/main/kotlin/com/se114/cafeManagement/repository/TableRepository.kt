package com.se114.cafeManagement.repository

import com.se114.cafeManagement.entity.CafeTable
import com.se114.cafeManagement.entity.TableStatus
import org.springframework.data.jpa.repository.JpaRepository

interface TableRepository : JpaRepository<CafeTable, Long> {
    fun findByTableCode(tableCode: String): CafeTable?
    fun findAllByStatus(status: TableStatus): List<CafeTable>
}
