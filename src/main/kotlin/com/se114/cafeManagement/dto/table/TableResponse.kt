package com.se114.cafeManagement.dto.table

import com.se114.cafeManagement.entity.CafeTable
import com.se114.cafeManagement.entity.TableStatus

data class TableResponse(
    val id: Long?,
    val name: String,
    val tableCode: String,
    val status: TableStatus
) {
    companion object {
        fun from(table: CafeTable) = TableResponse(
            id = table.id,
            name = table.name,
            tableCode = table.tableCode,
            status = table.status
        )
    }
}
