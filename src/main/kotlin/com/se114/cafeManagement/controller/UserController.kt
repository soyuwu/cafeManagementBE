package com.se114.cafeManagement.controller

import com.se114.cafeManagement.dto.user.CreateUserRequest
import com.se114.cafeManagement.dto.user.UserResponse
import com.se114.cafeManagement.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @Valid @RequestBody request: CreateUserRequest
    ): UserResponse {
        return userService.createUser(request)
    }

    @GetMapping
    fun getAllUsers(): List<UserResponse> {
        return userService.getAllUsers()
    }
}