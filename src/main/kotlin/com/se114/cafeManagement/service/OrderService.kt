package com.se114.cafeManagement.service

import com.se114.cafeManagement.dto.order.*
import com.se114.cafeManagement.entity.*
import com.se114.cafeManagement.repository.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productRepository: ProductRepository,
    private val variantRepository: ProductVariantRepository,
    private val tableRepository: TableRepository,
    private val userRepository: UserRepository,
    private val historyRepository: OrderStatusHistoryRepository,
    private val paymentRepository: PaymentRepository,
    private val paymentMethodRepository: PaymentMethodRepository
) {
    @Transactional
    fun createCustomerOrder(customerId: Long, request: CreateCustomerOrderRequest): OrderResponse {
        val customer = actor(customerId, UserRole.CUSTOMER)
        val table = tableRepository.findById(request.tableId).orElseThrow { notFound("Table", request.tableId) }
        if (table.status != TableStatus.AVAILABLE) throw ResponseStatusException(HttpStatus.CONFLICT, "Table is not available: ${table.id}")
        return create(OrderSource.CUSTOMER_APP, customer, null, table, request.items, request.note)
    }

    @Transactional
    fun createStaffOrder(staffId: Long, request: CreateStaffOrderRequest): OrderResponse {
        val staff = staffActor(staffId)
        val table = request.tableId?.let { tableRepository.findById(it).orElseThrow { notFound("Table", it) } }
        if (table != null && table.status != TableStatus.AVAILABLE) throw ResponseStatusException(HttpStatus.CONFLICT, "Table is not available: ${table.id}")
        return create(OrderSource.STAFF, null, staff, table, request.items, request.note)
    }

    private fun create(source: OrderSource, customer: User?, creator: User?, table: CafeTable?, items: List<CreateOrderItemRequest>, note: String?): OrderResponse {
        val order = orderRepository.save(Order(orderCode = "CF${System.currentTimeMillis()}", orderSource = source, customer = customer, createdBy = creator, table = table, note = note?.trim()?.takeIf { it.isNotEmpty() }))
        var subtotal = BigDecimal.ZERO
        val savedItems = items.map { request ->
            val product = productRepository.findById(request.productId).orElseThrow { notFound("Product", request.productId) }
            if (!product.isActive || !product.isAvailable) throw ResponseStatusException(HttpStatus.CONFLICT, "Product is unavailable: ${product.id}")
            val variant = variantRepository.findById(request.variantId).orElseThrow { notFound("Product variant", request.variantId) }
            if (variant.product.id != product.id || !variant.isActive) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Variant does not belong to an active product")
            val lineTotal = variant.price.multiply(BigDecimal.valueOf(request.quantity.toLong()))
            subtotal = subtotal.add(lineTotal)
            orderItemRepository.save(OrderItem(order, product.name, variant.price, request.quantity, lineTotal, request.note?.trim()?.takeIf { it.isNotEmpty() }, product, variant, variant.size))
        }
        order.subtotal = subtotal
        order.totalAmount = subtotal
        if (table != null) table.status = TableStatus.OCCUPIED
        historyRepository.save(OrderStatusHistory(null, OrderStatus.PENDING.name, LocalDateTime.now(), order = order, changedBy = creator ?: customer))
        return response(order, savedItems)
    }

    @Transactional(readOnly = true)
    fun getCustomerOrder(customerId: Long, id: Long): OrderResponse {
        actor(customerId, UserRole.CUSTOMER)
        val order = findOrder(id)
        if (order.customer?.id != customerId) throw ResponseStatusException(HttpStatus.FORBIDDEN, "Order does not belong to this customer")
        return response(order, orderItemRepository.findAllByOrderId(id))
    }
    @Transactional(readOnly = true)
    fun getMyOrders(customerId: Long): List<OrderResponse> { actor(customerId, UserRole.CUSTOMER); return orderRepository.findAllByCustomerIdOrderByCreatedAtDesc(customerId).map { response(it, orderItemRepository.findAllByOrderId(it.id!!)) } }
    @Transactional(readOnly = true)
    fun getStaffOrders(staffId: Long): List<OrderResponse> { staffActor(staffId); return orderRepository.findAllByOrderStatusInOrderByCreatedAtAsc(listOf(OrderStatus.PENDING, OrderStatus.PREPARING, OrderStatus.READY)).map { response(it, orderItemRepository.findAllByOrderId(it.id!!)) } }
    @Transactional(readOnly = true)
    fun getStatus(id: Long): OrderStatusResponse { val order = findOrder(id); return OrderStatusResponse(order.id, order.orderCode, order.orderStatus, order.paymentStatus, order.servedAt) }
    @Transactional(readOnly = true)
    fun getCustomerStatus(customerId: Long, id: Long): OrderStatusResponse { getCustomerOrder(customerId, id); return getStatus(id) }
    @Transactional(readOnly = true)
    fun getStaffOrder(staffId: Long, id: Long): OrderResponse { staffActor(staffId); return response(findOrder(id), orderItemRepository.findAllByOrderId(id)) }

    @Transactional
    fun updateStatus(staffId: Long, id: Long, request: UpdateOrderStatusRequest): OrderResponse {
        val staff = staffActor(staffId); val order = findOrder(id)
        val target = try { OrderStatus.valueOf(request.status.trim().uppercase()) } catch (_: IllegalArgumentException) { throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown order status") }
        if (!allowed(order.orderStatus, target)) throw ResponseStatusException(HttpStatus.CONFLICT, "Invalid status transition: ${order.orderStatus} to $target")
        val previous = order.orderStatus; order.orderStatus = target; order.updatedAt = LocalDateTime.now()
        if (target == OrderStatus.SERVED) { order.servedAt = LocalDateTime.now(); order.table?.status = TableStatus.AVAILABLE }
        if (target == OrderStatus.CANCELLED) order.table?.status = TableStatus.AVAILABLE
        historyRepository.save(OrderStatusHistory(previous.name, target.name, LocalDateTime.now(), order = order, changedBy = staff))
        return response(order, orderItemRepository.findAllByOrderId(id))
    }

    @Transactional
    fun recordPayment(staffId: Long, id: Long, request: RecordPaymentRequest): PaymentResponse {
        val staff = staffActor(staffId); val order = findOrder(id)
        if (order.paymentStatus != OrderPaymentStatus.UNPAID) throw ResponseStatusException(HttpStatus.CONFLICT, "Order is already paid")
        if (request.amount.compareTo(order.totalAmount) != 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment amount must equal order total")
        val method = paymentMethodRepository.findByCodeIgnoreCaseAndIsActiveTrue(request.method.trim()).takeIf { it != null }
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown active payment method: ${request.method}")
        val now = LocalDateTime.now(); order.paymentStatus = OrderPaymentStatus.PAID; order.paidAt = now; order.updatedAt = now
        return PaymentResponse.from(paymentRepository.save(Payment(order, staff, request.amount, PaymentStatus.SUCCESS, now, request.referenceCode?.trim()?.takeIf { it.isNotEmpty() }, method)))
    }
    @Transactional(readOnly = true)
    fun getPayments(staffId: Long, orderId: Long): List<PaymentResponse> { staffActor(staffId); findOrder(orderId); return paymentRepository.findAllByOrderIdOrderByConfirmedAtAsc(orderId).map(PaymentResponse::from) }

    private fun allowed(from: OrderStatus, to: OrderStatus) = (from == OrderStatus.PENDING && (to == OrderStatus.PREPARING || to == OrderStatus.CANCELLED)) || (from == OrderStatus.PREPARING && to == OrderStatus.READY) || (from == OrderStatus.READY && to == OrderStatus.SERVED)
    private fun actor(id: Long, role: UserRole) = userRepository.findById(id).orElseThrow { notFound("User", id) }.also { if (!it.isActive || it.role != role) throw ResponseStatusException(HttpStatus.FORBIDDEN, "User is not an active $role") }
    private fun staffActor(id: Long) = userRepository.findById(id).orElseThrow { notFound("User", id) }.also { if (!it.isActive || (it.role != UserRole.STAFF && it.role != UserRole.ADMIN)) throw ResponseStatusException(HttpStatus.FORBIDDEN, "User is not active staff") }
    private fun findOrder(id: Long) = orderRepository.findById(id).orElseThrow { notFound("Order", id) }
    private fun notFound(type: String, id: Long) = ResponseStatusException(HttpStatus.NOT_FOUND, "$type not found: $id")
    private fun response(order: Order, items: List<OrderItem>) = OrderResponse(order.id, order.orderCode, order.orderSource, order.customer?.id, order.table?.id, order.orderStatus, order.paymentStatus, order.subtotal, order.discountAmount, order.totalAmount, order.note, order.createdAt, order.paidAt, order.servedAt, items.map { OrderItemResponse(it.id, it.product.id, it.productName, it.variant.id, it.variantName, it.unitPrice, it.quantity, it.lineTotal, it.note) })
}
