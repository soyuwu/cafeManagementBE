package com.se114.cafeManagement.entity

import jakarta.persistence.*

@Entity @Table(name = "payment_methods")
class PaymentMethod(
    @Column(nullable = false, unique = true) var code: String,
    @Column(nullable = false) var name: String,
    @Column(name = "is_active", nullable = false) var isActive: Boolean = true,
    @Column(name = "sort_order", nullable = false) var sortOrder: Int = 0,
) { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null }
