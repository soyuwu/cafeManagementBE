package com.se114.cafeManagement.service

import com.se114.cafeManagement.dto.report.*
import com.se114.cafeManagement.entity.OrderPaymentStatus
import com.se114.cafeManagement.repository.OrderItemRepository
import com.se114.cafeManagement.repository.OrderRepository
import com.se114.cafeManagement.repository.PaymentRepository
import com.se114.cafeManagement.repository.UserRepository
import com.se114.cafeManagement.entity.UserRole
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

@Service
class ReportService(private val orderRepository: OrderRepository, private val orderItemRepository: OrderItemRepository, private val paymentRepository: PaymentRepository, private val userRepository: UserRepository) {
    @Transactional(readOnly = true)
    fun revenue(adminId: Long, from: LocalDate?, to: LocalDate?): RevenueReportResponse { admin(adminId)
        val orders = paidOrders(from, to)
        return RevenueReportResponse(orders.size.toLong(), orders.fold(BigDecimal.ZERO) { total, order -> total.add(order.totalAmount) })
    }
    @Transactional(readOnly = true)
    fun payments(adminId: Long, from: LocalDate?, to: LocalDate?): List<PaymentMethodReportResponse> { admin(adminId); return paymentRepository.findAll()
        .filter { inRange(it.confirmedAt.toLocalDate(), from, to) }
        .groupBy { it.paymentMethod.code }
        .map { (method, records) -> PaymentMethodReportResponse(method, records.fold(BigDecimal.ZERO) { total, payment -> total.add(payment.amount) }, records.size.toLong()) } }
    @Transactional(readOnly = true)
    fun topProducts(adminId: Long, from: LocalDate?, to: LocalDate?): List<TopProductResponse> { admin(adminId); return paidOrders(from, to).flatMap { order -> orderItemRepository.findAllByOrderId(order.id!!) }
        .groupBy { it.product.id to it.productName }
        .map { (product, items) -> TopProductResponse(product.first, product.second, items.sumOf { it.quantity }.toLong(), items.fold(BigDecimal.ZERO) { total, item -> total.add(item.lineTotal) }) }
        .sortedByDescending { it.revenue } }
    private fun paidOrders(from: LocalDate?, to: LocalDate?) = orderRepository.findAll().filter { it.paymentStatus == OrderPaymentStatus.PAID && inRange(it.paidAt?.toLocalDate(), from, to) }
    private fun inRange(date: LocalDate?, from: LocalDate?, to: LocalDate?) = date != null && (from == null || !date.isBefore(from)) && (to == null || !date.isAfter(to))
    private fun admin(id: Long) { val user = userRepository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: $id") }; if (!user.isActive || user.role != UserRole.ADMIN) throw ResponseStatusException(HttpStatus.FORBIDDEN, "User is not an active admin") }
}
