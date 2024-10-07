package com.ecommercedemo.userservice.dto.user

import com.ecommercedemo.userservice.validation.dateofbirth.ValidDateOfBirth
import com.ecommercedemo.userservice.validation.gender.Gender
import com.ecommercedemo.userservice.validation.name.ValidName
import com.ecommercedemo.userservice.validation.password.ValidPassword
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class RegisterUserDto(
    @field:NotBlank(message = "Username is mandatory")
    @field:Size(max = 50, message = "Username must be less than 50 characters")
    val userName: String,
    @ValidPassword
    val password: String,
    @ValidName
    val firstName: String,
    @ValidName
    val lastName: String,
    @Email
    val email: String,
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    @ValidDateOfBirth
    val dateOfBirth: LocalDate
)
