package com.se114.cafeManagement.service

import com.se114.cafeManagement.dto.table.CreateTableRequest
import com.se114.cafeManagement.dto.table.TableResponse
import com.se114.cafeManagement.entity.CafeTable
import com.se114.cafeManagement.repository.TableRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class TableService(
    private val tableRepository: TableRepository
){
    @Transactional
    fun createTable(request: CreateTableRequest): TableResponse {
        val tableCode = request.tableCode.trim()
        if (tableRepository.findByTableCode(tableCode) != null){
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Table exists: $tableCode"
            )
        }
        val table = CafeTable(
            name = request.name.trim(),
            tableCode = tableCode,
        )
        return TableResponse.from(tableRepository.save(table))
    }

    @Transactional(readOnly = true)
    fun getAllTables(): List<TableResponse> = tableRepository.findAll().map(TableResponse::from)
}
