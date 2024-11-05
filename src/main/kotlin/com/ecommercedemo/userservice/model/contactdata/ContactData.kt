package com.ecommercedemo.userservice.model.contactdata

import com.ecommercedemo.common.kafka.ChangedProperty
import com.ecommercedemo.common.model.BaseEntity
import com.ecommercedemo.common.validation.country.Country
import com.ecommercedemo.common.validation.email.ValidEmail
import com.ecommercedemo.common.validation.name.ValidName
import com.ecommercedemo.common.validation.phone.ValidPhone
import com.ecommercedemo.common.validation.specialcharacters.ExcludeSpecialCharacters
import com.ecommercedemo.userservice.model.user.User
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

    fun getChangedProperties(): List<ChangedProperty> {
        val properties = mutableListOf<ChangedProperty>()
        properties.add(ChangedProperty("id", id))
        properties.add(ChangedProperty("createdAt", createdAt))
        properties.add(ChangedProperty("updatedAt", updatedAt))
        properties.add(ChangedProperty("firstName", firstName))
        properties.add(ChangedProperty("lastName", lastName))
        properties.add(ChangedProperty("email", email))
        phoneNumber?.let { properties.add(ChangedProperty("phoneNumber", it)) }
        street?.let { properties.add(ChangedProperty("street", it)) }
        houseNumber?.let { properties.add(ChangedProperty("houseNumber", it)) }
        additionalAddressInfo?.let { properties.add(ChangedProperty("additionalAddressInfo", it)) }
        zipCode?.let { properties.add(ChangedProperty("zipCode", it)) }
        city?.let { properties.add(ChangedProperty("city", it)) }
        country?.let { properties.add(ChangedProperty("country", it)) }
        return properties
    }

}
