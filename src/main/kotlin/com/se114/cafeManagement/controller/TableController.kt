package com.se114.cafeManagement.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid
import com.se114.cafeManagement.dto.table.CreateTableRequest
import com.se114.cafeManagement.dto.table.TableResponse
import com.se114.cafeManagement.service.TableService

@RestController
@RequestMapping("/api/tables")
class TableController(
    private val tableService: TableService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTable(@Valid @RequestBody request: CreateTableRequest): TableResponse =
        tableService.createTable(request)

    @GetMapping
    fun getAllTables(): List<TableResponse> = tableService.getAllTables()
}
