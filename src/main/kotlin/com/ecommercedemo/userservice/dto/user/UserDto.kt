package com.ecommercedemo.userservice.dto.user

import com.ecommercedemo.common.validation.gender.Gender
import com.ecommercedemo.common.validation.userrole.UserRole
import java.time.LocalDate
import java.util.*

data class UserDto(
    val id: UUID,
    val username: String,
    val userRole: UserRole,
    val gender: Gender?,
    val dateOfBirth: LocalDate?
)
