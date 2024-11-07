package com.ecommercedemo.userservice.persistence.contactdata

import com.ecommercedemo.common.model.CustomProperty
import com.ecommercedemo.common.model.embedded.CustomPropertyData
import com.ecommercedemo.userservice.model.contactdata.UserInfo
import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import com.ecommercedemo.userservice.persistence.customProperty.CustomPropertyAdapterPostgresql
import org.springframework.stereotype.Service
import java.util.*

@Service
class ContactDataAdapterPostgresql(
    private val contactDataRepository: ContactDataRepository,
    private val customPropertyAdapterPostgresql: CustomPropertyAdapterPostgresql,
) : IContactDataAdapter {

    override fun getAllContactData(): List<UserInfo> {
        return contactDataRepository.findAll()
    }

    override fun saveContactData(data: UserInfo): UserInfo {
        return contactDataRepository.save(data)
    }

    override fun getContactDataByUserId(id: UUID): UserInfo? {
        return contactDataRepository.findById(id).orElseThrow()
    }

    override fun updateContactDataByUserId(id: UUID, data: UserInfo): UserInfo? {
        return contactDataRepository.updateContactDataByUser_Id(id, data)

    }

    override fun deleteContactDataByUserId(id: UUID) {
        contactDataRepository.deleteContactDataByUser_Id(id)
    }

    override fun existsByMail(email: String): Boolean {
        return contactDataRepository.existsByEmail(email)
    }

    override fun addCustomPropertyToAllContactData(customProperty: UserServicePseudoProperty) {
        val contactData = contactDataRepository.findAll()
        contactData.forEach { data ->
            val exists = data.customProperties.any { it.key == customProperty.key }
            if (exists) throw IllegalArgumentException("Property with key ${customProperty.key} already exists")
            data.customProperties.add(
                CustomPropertyData.serialize(
                    entity = UserInfo::class.simpleName!!,
                    key = customProperty.key,
                    value = null
                )
            )
            contactDataRepository.save(data)
        }
    }

    override fun removeCustomPropertyFromAllContactData(property: CustomProperty) {
        val isPresent = customPropertyAdapterPostgresql.existsByEntityAndKey(UserInfo::class.simpleName!!, property.key)
        if (!isPresent) throw IllegalArgumentException("Property with key ${property.key} does not exist")
        val contactData = contactDataRepository.findAll()
        contactData.forEach { data ->
            data.customProperties.removeIf { it.key == property.key }
            contactDataRepository.save(data)
        }
    }

    override fun renameCustomPropertyForAllContactData(key: String, newKey: String) {
        val allContactData = getAllContactData()
        val exists = allContactData.any { data -> data.customProperties.any { it.key == key } }
        if (!exists) throw IllegalArgumentException("Property with key $key does not exist")
        val newKeyExists = allContactData.any { data -> data.customProperties.any { it.key == newKey } }
        if (newKeyExists) throw IllegalArgumentException("Property with key $newKey already exists")
        allContactData.forEach { user ->
            user.customProperties.forEach {
                if (it.key == key) {
                    it.key = newKey
                }
            }
            contactDataRepository.save(user)
        }
    }
}