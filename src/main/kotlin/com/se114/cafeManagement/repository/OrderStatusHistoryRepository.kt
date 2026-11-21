package com.se114.cafeManagement.repository

import com.se114.cafeManagement.entity.OrderStatusHistory
import org.springframework.data.jpa.repository.JpaRepository

interface OrderStatusHistoryRepository : JpaRepository<OrderStatusHistory, Long>
