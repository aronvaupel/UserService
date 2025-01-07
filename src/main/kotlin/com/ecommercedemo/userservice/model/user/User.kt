package com.ecommercedemo.userservice.model.user

import com.ecommercedemo.common.application.validation.password.PasswordCrypto
import com.ecommercedemo.common.application.validation.password.PasswordValidator
import com.ecommercedemo.common.application.validation.password.ValidPassword
import com.ecommercedemo.common.application.validation.userrole.UserRole
import com.ecommercedemo.common.model.abstraction.AugmentableBaseEntity
import com.ecommercedemo.userservice.dto.user.UserResponseDto
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.*


@Entity
@Suppress("unused")
@Table(name = "users")
open class User(
    @field:NotBlank(message = "Username is mandatory")
    @field:Size(max = 50, message = "Username must be less than 50 characters")
    open var username: String = "",

    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    private var _password: String = "",

    @Enumerated(EnumType.ORDINAL)
    open var userRole: UserRole = UserRole.GUEST,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_permissions",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    @Column(name = "permission_id", nullable = false)
    open var permissions: List<UUID> = listOf(),

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_info_id", referencedColumnName = "id")
    open var userInfo: UserInfo? = null,

    open var lastActive: LocalDateTime = LocalDateTime.now()
    ) : AugmentableBaseEntity() {

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

}
