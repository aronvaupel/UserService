package com.ecommercedemo.userservice.model.user

import com.ecommercedemo.common.model.BaseEntity
import com.ecommercedemo.common.model.embedded.PseudoPropertyData
import com.ecommercedemo.common.validation.password.PasswordCrypto
import com.ecommercedemo.common.validation.password.PasswordValidator
import com.ecommercedemo.common.validation.password.ValidPassword
import com.ecommercedemo.common.validation.userrole.UserRole
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

    ) : BaseEntity() {
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
        pseudoProperties: MutableSet<PseudoPropertyData> = this.pseudoProperties.toMutableSet()
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
