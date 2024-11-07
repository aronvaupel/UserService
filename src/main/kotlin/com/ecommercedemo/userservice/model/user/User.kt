package com.ecommercedemo.userservice.model.user

import com.ecommercedemo.common.kafka.ChangedProperty
import com.ecommercedemo.common.model.BaseEntity
import com.ecommercedemo.common.validation.password.PasswordCrypto
import com.ecommercedemo.common.validation.password.PasswordValidator
import com.ecommercedemo.common.validation.password.ValidPassword
import com.ecommercedemo.common.validation.userrole.UserRole
import com.ecommercedemo.userservice.dto.user.UserDto
import com.ecommercedemo.userservice.model.contactdata.UserInfo
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime


@Entity
@Table(name = User.STORAGE_NAME)
@Suppress("unused")
open class User(
    @field:NotBlank(message = "Username is mandatory")
    @field:Size(max = 50, message = "Username must be less than 50 characters")
    open var username: String,

    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    private var _password: String,

    @Enumerated(EnumType.ORDINAL)
    open val userRole: UserRole,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "contact_data_id", referencedColumnName = "id")
    open val userInfo: UserInfo?,

    open var lastActive: LocalDateTime? = null

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

    fun toDto(): UserDto {
        return UserDto(
            id = id,
            username = username,
            userRole = userRole,
        )
    }

    fun getChangedProperties(): List<ChangedProperty> {
        val properties = mutableListOf<ChangedProperty>()
        properties.add(ChangedProperty("id", id))
        properties.add(ChangedProperty("createdAt", createdAt))
        properties.add(ChangedProperty("updatedAt", updatedAt))
        properties.add(ChangedProperty("username", username))
        properties.add(ChangedProperty("userRole", userRole))
        lastActive?.let { properties.add(ChangedProperty("lastActive", it)) }
        return properties
    }

}

