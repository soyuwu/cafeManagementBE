package com.se114.cafeManagement.repository

import com.se114.cafeManagement.entity.PaymentMethod
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentMethodRepository : JpaRepository<PaymentMethod, Long> {
    fun findByCodeIgnoreCaseAndIsActiveTrue(code: String): PaymentMethod?
}
