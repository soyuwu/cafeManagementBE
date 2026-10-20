package com.se114.cafeManagement.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity @Table(name = "product_variants")
class ProductVariant(
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "product_id", nullable = false) var product: Product,
    @Column(nullable = false) var size: String,
    @Column(nullable = false, precision = 19, scale = 2) var price: BigDecimal,
    @Column(name = "is_active", nullable = false) var isActive: Boolean = true,
) { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null }
