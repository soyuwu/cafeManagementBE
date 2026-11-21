package com.se114.cafeManagement.dto.order

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class CreateOrderItemRequest(
    @field:NotNull @field:Positive val productId: Long,
    @field:NotNull @field:Positive val variantId: Long,
    @field:Positive val quantity: Int,
    val note: String? = null
)

data class CreateCustomerOrderRequest(
    @field:NotNull @field:Positive val tableId: Long,
    @field:Valid @field:NotEmpty val items: List<CreateOrderItemRequest>,
    val note: String? = null
)

data class CreateStaffOrderRequest(
    val tableId: Long? = null,
    @field:Valid @field:NotEmpty val items: List<CreateOrderItemRequest>,
    val note: String? = null
)

data class UpdateOrderStatusRequest(@field:NotNull val status: String)

data class RecordPaymentRequest(
    @field:NotNull val method: String,
    @field:NotNull @field:Positive val amount: BigDecimal,
    val referenceCode: String? = null
)
