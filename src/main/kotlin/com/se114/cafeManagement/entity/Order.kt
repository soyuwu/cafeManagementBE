package com.se114.cafeManagement.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

enum class OrderSource { STAFF, CUSTOMER_APP }
enum class OrderStatus { PENDING, SERVED, CANCELLED }
enum class OrderPaymentStatus { UNPAID, PAID, REFUNDED }

@Entity @Table(name = "orders")
class Order(
    @Column(name = "order_code", nullable = false, unique = true) var orderCode: String,
    @Enumerated(EnumType.STRING) @Column(name = "order_source", nullable = false) var orderSource: OrderSource,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customer_id") var customer: User? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "created_by") var createdBy: User? = null,
    @Enumerated(EnumType.STRING) @Column(name = "order_status", nullable = false) var orderStatus: OrderStatus = OrderStatus.PENDING,
    @Enumerated(EnumType.STRING) @Column(name = "payment_status", nullable = false) var paymentStatus: OrderPaymentStatus = OrderPaymentStatus.UNPAID,
    @Column(nullable = false, precision = 19, scale = 2) var subtotal: BigDecimal = BigDecimal.ZERO,
    @Column(name = "discount_amount", nullable = false, precision = 19, scale = 2) var discountAmount: BigDecimal = BigDecimal.ZERO,
    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2) var totalAmount: BigDecimal = BigDecimal.ZERO,
    @Column(columnDefinition = "text") var note: String? = null,
    @Column(name = "created_at", nullable = false) var createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", nullable = false) var updatedAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "paid_at") var paidAt: LocalDateTime? = null,
    @Column(name = "served_at") var servedAt: LocalDateTime? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "table_id") var table: CafeTable? = null,
) { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null }
