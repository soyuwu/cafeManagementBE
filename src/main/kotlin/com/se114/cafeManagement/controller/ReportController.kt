package com.se114.cafeManagement.controller

import com.se114.cafeManagement.service.ReportService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestHeader
import java.time.LocalDate

@RestController
@RequestMapping("/api/admin/reports")
class ReportController(private val reportService: ReportService) {
    @GetMapping("/revenue") fun revenue(@RequestHeader("X-User-Id") adminId: Long, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate?, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate?) = reportService.revenue(adminId, from, to)
    @GetMapping("/payments") fun payments(@RequestHeader("X-User-Id") adminId: Long, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate?, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate?) = reportService.payments(adminId, from, to)
    @GetMapping("/top-products") fun topProducts(@RequestHeader("X-User-Id") adminId: Long, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate?, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate?) = reportService.topProducts(adminId, from, to)
}
