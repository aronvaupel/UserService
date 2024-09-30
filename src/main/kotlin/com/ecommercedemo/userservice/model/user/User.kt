package com.ecommercedemo.userservice.model.user

import com.ecommercedemo.userservice.model.BaseEntity
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


@Entity
@Table(name = User.COLLECTION_NAME)
class User(
    @field:NotBlank(message = "Username is mandatory")
    @field:Size(max = 50, message = "Username must be less than 50 characters")
    val userName: String,

    @ValidPassword
    private var _password: String,

    @Enumerated(EnumType.ORDINAL)
    val userRole: UserRole,

    @OneToOne
    @JoinColumn(name = "contact_data_id")
    val contactData: ContactData?,

    @Enumerated(EnumType.ORDINAL)
    val gender: Gender?,

    @ValidDateOfBirth
    val dateOfBirth: LocalDate?
) : BaseEntity() {
    companion object {
        const val COLLECTION_NAME = "users"
    }

    var password: String
        get() = _password
        set(value) {
            if (!PasswordValidator.isValid(value, null)) {
                throw IllegalArgumentException("Password does not meet the requirements")
            }
            _password = PasswordCrypto.hashPassword(value)
        }

}

