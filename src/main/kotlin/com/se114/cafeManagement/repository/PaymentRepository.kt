package com.se114.cafeManagement.repository

import com.se114.cafeManagement.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<Payment, Long> {
    fun findAllByOrderIdOrderByConfirmedAtAsc(orderId: Long): List<Payment>
}
