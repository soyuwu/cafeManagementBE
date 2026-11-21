package com.se114.cafeManagement.controller

import com.se114.cafeManagement.dto.user.LoginRequest
import com.se114.cafeManagement.dto.user.UserResponse
import com.se114.cafeManagement.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val userService: UserService) {
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): UserResponse = userService.login(request)
}
