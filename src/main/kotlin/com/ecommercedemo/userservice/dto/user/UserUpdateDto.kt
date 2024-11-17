package com.ecommercedemo.userservice.dto.user

import com.ecommercedemo.common.application.validation.password.ValidPassword
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.*

data class UserUpdateDto(
    @NotBlank(message = "Id may not be blank")
    @NotNull(message = "Id is mandatory")
    val id: UUID,
    @field:NotBlank(message = "Username is mandatory")
    @field:Size(max = 50, message = "Username must be less than 50 characters")
    val username: String?,
    @field:ValidPassword
    val password: String?,
)
