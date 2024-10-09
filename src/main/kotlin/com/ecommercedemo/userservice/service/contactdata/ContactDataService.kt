package com.ecommercedemo.userservice.service.contactdata

import com.ecommercedemo.userservice.model.contactdata.ContactData
import com.ecommercedemo.userservice.model.customProperty.UserServiceCustomProperty
import com.ecommercedemo.userservice.persistence.contactdata.IContactDataAdapter
import org.springframework.stereotype.Service
import java.util.*

@Service
class ContactDataService(
    private val contactDataAdapter: IContactDataAdapter
) {
    fun getAllContactData(): List<ContactData> {
        return contactDataAdapter.getAllContactData()
    }

    fun getContactDataByUserId(id: UUID): ContactData? {
        return contactDataAdapter.getContactDataByUserId(id)
    }

    fun saveContactData(data: ContactData): ContactData {
        return contactDataAdapter.saveContactData(data)
    }

    fun updateContactDataByUserId(id: UUID, data: ContactData): ContactData? {
        return contactDataAdapter.updateContactDataByUserId(id, data)
    }

    fun deleteContactDataByUserId(id: UUID) {
        contactDataAdapter.deleteContactDataByUserId(id)
    }

    fun addCustomPropertyToAllContactData(customProperty: UserServiceCustomProperty<*>): UserServiceCustomProperty<*> {
        contactDataAdapter.addCustomPropertyToAllContactData(customProperty)
        return customProperty
    }

    fun renameCustomPropertyForAllContactData(key: String, newKey: String) {
        contactDataAdapter.renameCustomPropertyForAllContactData(key, newKey)
    }
}