package com.se114.cafeManagement.dto.table

import jakarta.validation.constraints.NotBlank

data class CreateTableRequest(
    @field:NotBlank
    val tableCode: String,
    @field:NotBlank
    val name: String
)
