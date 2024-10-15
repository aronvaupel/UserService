package com.ecommercedemo.userservice.persistence.contactdata

import com.ecommercedemo.common.model.CustomProperty
import com.ecommercedemo.userservice.model.contactdata.ContactData
import com.ecommercedemo.userservice.model.customProperty.UserServiceCustomProperty
import java.util.*

interface IContactDataAdapter {
    fun getAllContactData(): List<ContactData>
    fun saveContactData(data: ContactData): ContactData
    fun getContactDataByUserId(id:UUID): ContactData?
    fun updateContactDataByUserId(id:UUID, data: ContactData): ContactData?
    fun deleteContactDataByUserId(id: UUID)
    fun existsByMail(email: String): Boolean
    fun addCustomPropertyToAllContactData(customProperty: UserServiceCustomProperty)
    fun removeCustomPropertyFromAllContactData(property: CustomProperty)
    fun renameCustomPropertyForAllContactData(key: String, newKey: String)
}