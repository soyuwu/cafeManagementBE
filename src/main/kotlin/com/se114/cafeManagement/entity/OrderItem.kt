package com.se114.cafeManagement.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity @Table(name = "order_items")
class OrderItem(
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "order_id", nullable = false) var order: Order,
    @Column(name = "product_name", nullable = false) var productName: String,
    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2) var unitPrice: BigDecimal,
    @Column(nullable = false) var quantity: Int,
    @Column(name = "line_total", nullable = false, precision = 19, scale = 2) var lineTotal: BigDecimal,
    @Column(columnDefinition = "text") var note: String? = null,
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "product_id", nullable = false) var product: Product,
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "variant_id", nullable = false) var variant: ProductVariant,
    @Column(name = "variant_name", nullable = false) var variantName: String,
) { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null }
