package com.se114.cafeManagement.entity

import jakarta.persistence.*

enum class TableStatus { AVAILABLE, OCCUPIED }

@Entity @Table(name = "tables")
class CafeTable(
    @Column(name = "table_code", nullable = false, unique = true) var tableCode: String,
    @Column(nullable = false) var name: String,
    @Enumerated(EnumType.STRING) @Column(nullable = false) var status: TableStatus = TableStatus.AVAILABLE,
) { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null }
