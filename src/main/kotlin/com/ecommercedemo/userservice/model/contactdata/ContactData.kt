package com.ecommercedemo.userservice.model.contactdata

import com.ecommercedemo.userservice.model.BaseEntity
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.validation.country.Country
import com.ecommercedemo.userservice.validation.email.ValidEmail
import com.ecommercedemo.userservice.validation.name.ValidName
import com.ecommercedemo.userservice.validation.phone.ValidPhone
import com.ecommercedemo.userservice.validation.specialcharacters.ExcludeSpecialCharacters
import jakarta.persistence.*
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
    val phoneNumber: String?,

    @field:Size(max = 10, message = "House number must be less than 100 characters")
    @ExcludeSpecialCharacters
    val street: String?,

    @field:Size(max = 10, message = "House number must be less than 10 characters")
    @ExcludeSpecialCharacters
    val houseNumber: String?,

    @field:Size(max = 100, message = "Additional address info must be less than 100 characters")
    val additionalAddressInfo: String?,

    @field:Size(max = 20, message = "ZIP code must be less than 20 characters")
    @ExcludeSpecialCharacters
    val zipCode: String?,

    @field:Size(max = 50, message = "City must be less than 50 characters")
    @ExcludeSpecialCharacters
    val city: String?,

    @Enumerated(EnumType.ORDINAL)
    val country: Country?,

    @OneToOne(mappedBy = "contactData")
    val user: User
) : BaseEntity() {
    companion object {
        const val COLLECTION_NAME = "contact_data"
    }

}
