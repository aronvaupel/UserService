package com.ecommercedemo.userservice.persistence.contactdata

import com.ecommercedemo.common.model.CustomProperty
import com.ecommercedemo.userservice.model.contactdata.UserInfo
import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import java.util.*

interface IContactDataAdapter {
    fun getAllContactData(): List<UserInfo>
    fun saveContactData(data: UserInfo): UserInfo
    fun getContactDataByUserId(id:UUID): UserInfo?
    fun updateContactDataByUserId(id:UUID, data: UserInfo): UserInfo?
    fun deleteContactDataByUserId(id: UUID)
    fun existsByMail(email: String): Boolean
    fun addCustomPropertyToAllContactData(customProperty: UserServicePseudoProperty)
    fun removeCustomPropertyFromAllContactData(property: CustomProperty)
    fun renameCustomPropertyForAllContactData(key: String, newKey: String)
}