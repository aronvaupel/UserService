package com.ecommercedemo.userservice.dto.user

import com.ecommercedemo.common.application.validation.userrole.UserRole
import java.util.*

data class UserResponseDto(
    val id: UUID,
    val username: String,
    val userRole: UserRole,
)
