package com.se114.cafeManagement.entity

import jakarta.persistence.*

@Entity @Table(name = "tables")
class CafeTable(
    @Column(name = "table_code", nullable = false, unique = true) var tableCode: String,
    @Column(nullable = false) var name: String,
) { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null }
