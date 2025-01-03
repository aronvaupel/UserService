package com.ecommercedemo.userservice.dto.user


import com.ecommercedemo.common.application.validation.email.ValidEmail
import com.ecommercedemo.common.application.validation.name.ValidName
import com.ecommercedemo.common.application.validation.password.ValidPassword
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class UserRegisterDto(
    @field:NotBlank(message = "Username is mandatory")
    @field:Size(max = 50, message = "Username must be less than 50 characters")
    val username: String,
    @field:ValidPassword
    val password: String,
    @field:ValidName
    val firstName: String,
    @field:ValidName
    val lastName: String,
    @field:ValidEmail
    val email: String,
)

