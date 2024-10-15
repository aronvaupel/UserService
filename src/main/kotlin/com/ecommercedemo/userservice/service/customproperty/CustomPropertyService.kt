package com.ecommercedemo.userservice.service.customproperty

import com.ecommercedemo.userservice.model.contactdata.ContactData
import com.ecommercedemo.userservice.model.customProperty.UserServiceCustomProperty
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.persistence.customProperty.ICustomPropertyAdapter
import com.ecommercedemo.userservice.persistence.user.UserAdapterPostgresql
import com.ecommercedemo.userservice.service.contactdata.ContactDataService
import org.springframework.stereotype.Service

@Service
class CustomPropertyService(
    private val contactDataService: ContactDataService,
    private val userAdapterPostgresql: UserAdapterPostgresql,
    private val customPropertyAdapter: ICustomPropertyAdapter,
) {
    fun getAllUserCustomProperties(): List<UserServiceCustomProperty> {
        return customPropertyAdapter.getAllUserCustomProperties()
    }

    fun getAllContactDataCustomProperties(): List<UserServiceCustomProperty> {
        return customPropertyAdapter.getAllContactDataCustomProperties()
    }

    fun getUserCustomPropertyByKey(key: String): UserServiceCustomProperty? {
        return customPropertyAdapter.getUserCustomPropertyByKey(key)
    }

    fun getContactDataCustomPropertyByKey(key: String): UserServiceCustomProperty? {
        return customPropertyAdapter.getContactDataCustomPropertyByKey(key)
    }

    fun deleteCustomPropertyByEntityAndKey(entity: String, key: String) {
        customPropertyAdapter.deleteCustomPropertyByEntityAndKey(entity, key)
    }

    fun addCustomPropertyToAllUsers(customProperty: UserServiceCustomProperty): UserServiceCustomProperty {
        val property = customPropertyAdapter.createCustomProperty(customProperty)
        userAdapterPostgresql.addCustomPropertyToAllUsers(property)
        return property
    }


    fun addCustomPropertyToAllContactData(customProperty: UserServiceCustomProperty): UserServiceCustomProperty {
        val property = customPropertyAdapter.createCustomProperty(customProperty)
        contactDataService.addCustomPropertyToAllContactData(property)
        return property
    }

    fun renameCustomProperty(entity: String, key: String, newKey: String) : UserServiceCustomProperty {
        val updated = customPropertyAdapter.renameCustomProperty(entity, key, newKey)
        when (entity) {
            User::class.simpleName -> userAdapterPostgresql.renameCustomPropertyForAllUsers(key, newKey)
            ContactData::class.simpleName -> contactDataService.renameCustomPropertyForAllContactData(key, newKey)
        }
        return updated
    }

    fun existsByEntityAndKey(entity: String, key: String): Boolean {
        return customPropertyAdapter.existsByEntityAndKey(entity, key)
    }
}