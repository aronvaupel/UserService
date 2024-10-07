package com.ecommercedemo.userservice.model.contactdata

import com.ecommercedemo.model.BaseEntity
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.validation.country.Country
import com.ecommercedemo.userservice.validation.email.ValidEmail
import com.ecommercedemo.userservice.validation.name.ValidName
import com.ecommercedemo.userservice.validation.phone.ValidPhone
import com.ecommercedemo.userservice.validation.specialcharacters.ExcludeSpecialCharacters
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = ContactData.COLLECTION_NAME)
class ContactData(
    @ValidName
    val firstName: String,

    @ValidName
    val lastName: String,

    @ValidEmail
    @Column(nullable = false, unique = true)
    val email: String,

    @ValidPhone
    val phoneNumber: String? = null,

    @field:Size(max = 10, message = "House number must be less than 100 characters")
    @ExcludeSpecialCharacters
    val street: String? = null,

    @field:Size(max = 10, message = "House number must be less than 10 characters")
    @ExcludeSpecialCharacters
    val houseNumber: String? = null,

    @field:Size(max = 100, message = "Additional address info must be less than 100 characters")
    val additionalAddressInfo: String? = null,

    @field:Size(max = 20, message = "ZIP code must be less than 20 characters")
    @ExcludeSpecialCharacters
    val zipCode: String? = null,

    @field:Size(max = 50, message = "City must be less than 50 characters")
    @ExcludeSpecialCharacters
    val city: String? = null,

    @Enumerated(EnumType.ORDINAL)
    val country: Country? = null,

    @OneToOne(mappedBy = "contactData")
    @NotNull
    val user: User? = null
) : BaseEntity() {

    companion object {
        const val COLLECTION_NAME = "contact_data"
    }

}
