package com.se114.cafeManagement.repository

import com.se114.cafeManagement.entity.Order
import com.se114.cafeManagement.entity.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findAllByCustomerIdOrderByCreatedAtDesc(customerId: Long): List<Order>
    fun findAllByOrderStatusInOrderByCreatedAtAsc(statuses: Collection<OrderStatus>): List<Order>
}
