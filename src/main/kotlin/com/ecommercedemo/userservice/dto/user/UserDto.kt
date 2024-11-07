package com.ecommercedemo.userservice.dto.user

import com.ecommercedemo.common.validation.userrole.UserRole
import java.util.*

data class UserDto(
    val id: UUID,
    val username: String,
    val userRole: UserRole,
)
