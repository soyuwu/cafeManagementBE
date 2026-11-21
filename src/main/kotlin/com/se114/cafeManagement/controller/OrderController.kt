package com.se114.cafeManagement.controller

import com.se114.cafeManagement.dto.order.*
import com.se114.cafeManagement.service.OrderService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestHeader("X-User-Id") customerId: Long, @Valid @RequestBody request: CreateCustomerOrderRequest) = orderService.createCustomerOrder(customerId, request)
    @GetMapping("/my")
    fun mine(@RequestHeader("X-User-Id") customerId: Long) = orderService.getMyOrders(customerId)
    @GetMapping("/{id}")
    fun get(@RequestHeader("X-User-Id") customerId: Long, @PathVariable id: Long) = orderService.getCustomerOrder(customerId, id)
    @GetMapping("/{id}/status")
    fun status(@RequestHeader("X-User-Id") customerId: Long, @PathVariable id: Long) = orderService.getCustomerStatus(customerId, id)
}

@RestController
@RequestMapping("/api/staff/orders")
class StaffOrderController(private val orderService: OrderService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestHeader("X-User-Id") staffId: Long, @Valid @RequestBody request: CreateStaffOrderRequest) = orderService.createStaffOrder(staffId, request)
    @GetMapping
    fun list(@RequestHeader("X-User-Id") staffId: Long) = orderService.getStaffOrders(staffId)
    @GetMapping("/{id}")
    fun get(@RequestHeader("X-User-Id") staffId: Long, @PathVariable id: Long) = orderService.getStaffOrder(staffId, id)
    @PatchMapping("/{id}/status")
    fun status(@RequestHeader("X-User-Id") staffId: Long, @PathVariable id: Long, @Valid @RequestBody request: UpdateOrderStatusRequest) = orderService.updateStatus(staffId, id, request)
    @PostMapping("/{id}/payments")
    @ResponseStatus(HttpStatus.CREATED)
    fun payment(@RequestHeader("X-User-Id") staffId: Long, @PathVariable id: Long, @Valid @RequestBody request: RecordPaymentRequest) = orderService.recordPayment(staffId, id, request)
    @GetMapping("/{id}/payments")
    fun payments(@RequestHeader("X-User-Id") staffId: Long, @PathVariable id: Long) = orderService.getPayments(staffId, id)
}
