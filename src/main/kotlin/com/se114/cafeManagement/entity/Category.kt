package com.se114.cafeManagement.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "categories")
class Category(
    @Column(nullable = false, unique = true) var name: String,
    @Column(name = "sort_order", nullable = false) var sortOrder: Int = 0,
    @Column(name = "is_active", nullable = false) var isActive: Boolean = true,
    @Column(name = "created_at", nullable = false) var createdAt: LocalDateTime = LocalDateTime.now(),
) { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null }
