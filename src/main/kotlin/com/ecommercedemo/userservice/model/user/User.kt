package com.ecommercedemo.userservice.model.user

import com.ecommercedemo.common.model.BaseEntity
import com.ecommercedemo.userservice.dto.user.UserDto
import com.ecommercedemo.userservice.model.contactdata.ContactData
import com.ecommercedemo.userservice.validation.dateofbirth.ValidDateOfBirth
import com.ecommercedemo.userservice.validation.gender.Gender
import com.ecommercedemo.userservice.validation.password.PasswordCrypto
import com.ecommercedemo.userservice.validation.password.PasswordValidator
import com.ecommercedemo.userservice.validation.password.ValidPassword
import com.ecommercedemo.userservice.validation.userrole.UserRole
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalDateTime


@Entity
@Table(name = User.STORAGE_NAME)
@Suppress("unused")
open class User(
    @field:NotBlank(message = "Username is mandatory")
    @field:Size(max = 50, message = "Username must be less than 50 characters")
    var username: String,

    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    private var _password: String,

    @Enumerated(EnumType.ORDINAL)
    val userRole: UserRole,

    @OneToOne
    @JoinColumn(name = "contact_data_id")
    val contactData: ContactData?,

    @Enumerated(EnumType.ORDINAL)
    val gender: Gender?,

    @ValidDateOfBirth
    val dateOfBirth: LocalDate?,

    var lastActive: LocalDateTime? = null

) : BaseEntity() {
    companion object {
        const val STORAGE_NAME = "users"
    }

    var password: String
        get() = _password
        set(value) {
            if (!PasswordValidator.isValid(value, null)) {
                throw IllegalArgumentException("Password does not meet the requirements")
            }
            _password = PasswordCrypto.hashPassword(value)
        }

    fun toDto(): UserDto {
        return UserDto(
            id = id,
            username = username,
            userRole = userRole,
            gender = gender,
            dateOfBirth = dateOfBirth,
        )
    }

}

