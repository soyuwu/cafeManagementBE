package com.se114.cafeManagement.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "products")
class Product(
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "category_id", nullable = false) var category: Category,
    @Column(nullable = false) var name: String,
    @Column(columnDefinition = "text") var description: String? = null,
    @Column(name = "image_url") var imageUrl: String? = null,
    @Column(name = "is_available", nullable = false) var isAvailable: Boolean = true,
    @Column(name = "is_active", nullable = false) var isActive: Boolean = true,
    @Column(name = "created_at", nullable = false) var createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", nullable = false) var updatedAt: LocalDateTime = LocalDateTime.now(),
) { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null }
