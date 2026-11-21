package com.se114.cafeManagement.dto.report

import java.math.BigDecimal

data class RevenueReportResponse(val paidOrderCount: Long, val revenue: BigDecimal)
data class PaymentMethodReportResponse(val method: String, val amount: BigDecimal, val paymentCount: Long)
data class TopProductResponse(val productId: Long?, val productName: String, val quantity: Long, val revenue: BigDecimal)
