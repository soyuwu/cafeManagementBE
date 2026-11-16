package com.se114.cafeManagement.dto.table

import com.se114.cafeManagement.entity.CafeTable

data class TableResponse(
    val id: Long?,
    val name: String,
    val tableCode: String
) {
    companion object {
        fun from(table: CafeTable) = TableResponse(
            id = table.id,
            name = table.name,
            tableCode = table.tableCode
        )
    }
}
