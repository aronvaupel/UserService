package com.ecommercedemo.userservice.model.userinfo

import com.ecommercedemo.common.application.validation.country.Country
import com.ecommercedemo.common.application.validation.email.ValidEmail
import com.ecommercedemo.common.application.validation.name.ValidName
import com.ecommercedemo.common.application.validation.phone.ValidPhone
import com.ecommercedemo.common.application.validation.specialcharacters.ExcludeSpecialCharacters
import com.ecommercedemo.common.model.abstraction.AugmentableBaseEntity
import com.ecommercedemo.userservice.model.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "user_info",
    uniqueConstraints = [UniqueConstraint(columnNames = ["email"])]
    )
open class UserInfo(
    @ValidName
    open val firstName: String,

    @ValidName
    open val lastName: String,

    @ValidEmail
    @Column(nullable = false, unique = true)
    open var email: String,

    @ValidPhone
    open val phoneNumber: String? = null,

    @field:Size(max = 100, message = "Street must be less than 100 characters")
    @ExcludeSpecialCharacters
    open val street: String? = null,

    @field:Size(max = 1000, message = "House number must be less than 1000 characters")
    @ExcludeSpecialCharacters
    open val houseNumber: String? = null,

    @field:Size(max = 100, message = "Additional address info must be less than 100 characters")
    open val additionalAddressInfo: String? = null,

    @field:Size(max = 20, message = "ZIP code must be less than 20 characters")
    @ExcludeSpecialCharacters
    open val zipCode: String? = null,

    @field:Size(max = 50, message = "City must be less than 50 characters")
    @ExcludeSpecialCharacters
    open val city: String? = null,

    @Enumerated(EnumType.ORDINAL)
    open val country: Country? = null,

    @OneToOne(mappedBy = "userInfo")
    @NotNull
    @JsonIgnore
    open val user: User? = null
) : AugmentableBaseEntity()