package com.se114.cafeManagement.dto.order

import com.se114.cafeManagement.entity.OrderPaymentStatus
import com.se114.cafeManagement.entity.OrderStatus
import com.se114.cafeManagement.entity.OrderSource
import com.se114.cafeManagement.entity.Payment
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderItemResponse(val id: Long?, val productId: Long?, val productName: String, val variantId: Long?, val variantName: String, val unitPrice: BigDecimal, val quantity: Int, val lineTotal: BigDecimal, val note: String?)
data class OrderResponse(
    val id: Long?, val orderCode: String, val orderSource: OrderSource, val customerId: Long?, val tableId: Long?,
    val orderStatus: OrderStatus, val paymentStatus: OrderPaymentStatus, val subtotal: BigDecimal, val discountAmount: BigDecimal,
    val totalAmount: BigDecimal, val note: String?, val createdAt: LocalDateTime, val paidAt: LocalDateTime?, val servedAt: LocalDateTime?, val items: List<OrderItemResponse>
)
data class OrderStatusResponse(val id: Long?, val orderCode: String, val orderStatus: OrderStatus, val paymentStatus: OrderPaymentStatus, val servedAt: LocalDateTime?)
data class PaymentResponse(val id: Long?, val method: String, val amount: BigDecimal, val status: String, val confirmedBy: Long?, val confirmedAt: LocalDateTime, val referenceCode: String?) {
    companion object { fun from(payment: Payment) = PaymentResponse(payment.id, payment.paymentMethod.code, payment.amount, payment.paymentStatus.name, payment.confirmedBy.id, payment.confirmedAt, payment.referenceCode) }
}
