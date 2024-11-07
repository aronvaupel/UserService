package com.ecommercedemo.userservice.service.contactdata

import com.ecommercedemo.userservice.model.contactdata.UserInfo
import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import com.ecommercedemo.userservice.persistence.contactdata.IContactDataAdapter
import org.springframework.stereotype.Service
import java.util.*

@Service
class ContactDataService(
    private val contactDataAdapter: IContactDataAdapter
) {
    fun getAllContactData(): List<UserInfo> {
        return contactDataAdapter.getAllContactData()
    }

    fun getContactDataByUserId(id: UUID): UserInfo? {
        return contactDataAdapter.getContactDataByUserId(id)
    }

    fun saveContactData(data: UserInfo): UserInfo {
        return contactDataAdapter.saveContactData(data)
    }

    fun updateContactDataByUserId(id: UUID, data: UserInfo): UserInfo? {
        return contactDataAdapter.updateContactDataByUserId(id, data)
    }

    fun deleteContactDataByUserId(id: UUID) {
        contactDataAdapter.deleteContactDataByUserId(id)
    }

    fun addCustomPropertyToAllContactData(customProperty: UserServicePseudoProperty): UserServicePseudoProperty {
        contactDataAdapter.addCustomPropertyToAllContactData(customProperty)
        return customProperty
    }

    fun renameCustomPropertyForAllContactData(key: String, newKey: String) {
        contactDataAdapter.renameCustomPropertyForAllContactData(key, newKey)
    }
}