package com.se114.cafeManagement.service

import com.se114.cafeManagement.dto.user.CreateUserRequest
import com.se114.cafeManagement.dto.user.UserResponse
import com.se114.cafeManagement.entity.User
import com.se114.cafeManagement.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    @Transactional
    fun createUser(request: CreateUserRequest): UserResponse {
        require(userRepository.findByEmail(request.email) == null) {
            "Email đã được sử dụng"
        }

        val user = User(
            fullName = request.fullName,
            phone = request.phone,
            email = request.email,
            passwordHash = requireNotNull(passwordEncoder.encode(request.password)) {
                "Khong the ma hoa mat khau"
            },
            role = request.role
        )

        return UserResponse.from(userRepository.save(user))
    }

    @Transactional(readOnly = true)
    fun getAllUsers(): List<UserResponse> {
        return userRepository.findAll().map { UserResponse.from(it) }
    }
}
