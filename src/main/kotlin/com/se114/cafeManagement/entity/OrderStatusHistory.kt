package com.se114.cafeManagement.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "order_status_histories")
class OrderStatusHistory(
    @Column(name = "from_status") var fromStatus: String? = null,
    @Column(name = "to_status", nullable = false) var toStatus: String,
    @Column(name = "changed_at", nullable = false) var changedAt: LocalDateTime,
    @Column(columnDefinition = "text") var note: String? = null,
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "order_id", nullable = false) var order: Order,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "changed_by") var changedBy: User? = null,
) { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null }
