package com.ecommercedemo.userservice.model.user

import com.ecommercedemo.common.application.validation.password.PasswordCrypto
import com.ecommercedemo.common.application.validation.password.PasswordValidator
import com.ecommercedemo.common.application.validation.password.ValidPassword
import com.ecommercedemo.common.application.validation.userrole.UserRole
import com.ecommercedemo.common.model.ExtendableBaseEntity
import com.ecommercedemo.userservice.dto.user.UserResponseDto
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = User.STORAGE_NAME)
@Suppress("unused")
open class User(
    override val id: UUID = UUID.randomUUID(),
    @field:NotBlank(message = "Username is mandatory")
    @field:Size(max = 50, message = "Username must be less than 50 characters")
    open var username: String,

    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    private var _password: String,

    @Enumerated(EnumType.ORDINAL)
    open val userRole: UserRole,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_info_id", referencedColumnName = "id")
    open val userInfo: UserInfo? = null,

    open var lastActive: LocalDateTime = LocalDateTime.now(),

    ) : ExtendableBaseEntity() {
    companion object {
        const val STORAGE_NAME = "users"
    }

    open var password: String
        get() = _password
        set(value) {
            if (!PasswordValidator.isValid(value, null)) {
                throw IllegalArgumentException("Password does not meet the requirements")
            }
            _password = PasswordCrypto.hashPassword(value)
        }

    fun toDto(): UserResponseDto {
        return UserResponseDto(
            id = id,
            username = username,
            userRole = userRole,
        )
    }

    open fun copy(
        id: UUID = this.id,
        username: String = this.username,
        password: String = this.password,
        userRole: UserRole = this.userRole,
        userInfo: UserInfo? = this.userInfo,
        lastActive: LocalDateTime = this.lastActive,
        createdAt: LocalDateTime = this.createdAt,
        updatedAt: LocalDateTime = this.updatedAt,
        pseudoProperties: String = this.pseudoProperties
    ): User {
        val copiedUser = User(
            id = id,
            username = username,
            _password = password,
            userRole = userRole,
            userInfo = userInfo,
            lastActive = lastActive
        )
        copiedUser.createdAt = createdAt
        copiedUser.updatedAt = updatedAt
        copiedUser.pseudoProperties = pseudoProperties
        return copiedUser
    }

}
