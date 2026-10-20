package com.se114.cafeManagement.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

enum class PaymentStatus { SUCCESS, FAILED, REFUNDED }

@Entity @Table(name = "payments")
class Payment(
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "order_id", nullable = false) var order: Order,
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "confirmed_by", nullable = false) var confirmedBy: User,
    @Column(nullable = false, precision = 19, scale = 2) var amount: BigDecimal,
    @Enumerated(EnumType.STRING) @Column(name = "payment_status", nullable = false) var paymentStatus: PaymentStatus,
    @Column(name = "confirmed_at", nullable = false) var confirmedAt: LocalDateTime,
    @Column(name = "reference_code") var referenceCode: String? = null,
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "payment_method_id", nullable = false) var paymentMethod: PaymentMethod,
) { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null }
