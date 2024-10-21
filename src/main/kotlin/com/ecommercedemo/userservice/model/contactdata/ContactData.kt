package com.ecommercedemo.userservice.model.contactdata

import com.ecommercedemo.common.model.BaseEntity
import com.ecommercedemo.common.validation.country.Country
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.validation.email.ValidEmail
import com.ecommercedemo.userservice.validation.name.ValidName
import com.ecommercedemo.userservice.validation.phone.ValidPhone
import com.ecommercedemo.userservice.validation.specialcharacters.ExcludeSpecialCharacters
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = ContactData.STORAGE_NAME)
open class ContactData(
    @ValidName
    open val firstName: String,

    @ValidName
    open val lastName: String,

    @ValidEmail
    @Column(nullable = false, unique = true)
    open var email: String,

    @ValidPhone
    open val phoneNumber: String? = null,

    @field:Size(max = 10, message = "House number must be less than 100 characters")
    @ExcludeSpecialCharacters
    open val street: String? = null,

    @field:Size(max = 10, message = "House number must be less than 10 characters")
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

    @OneToOne(mappedBy = "contactData")
    @NotNull
    open val user: User? = null
) : BaseEntity() {

    companion object {
        const val STORAGE_NAME = "contact_data"
    }

}
