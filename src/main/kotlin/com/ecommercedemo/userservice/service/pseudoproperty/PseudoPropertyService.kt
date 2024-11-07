package com.ecommercedemo.userservice.service.pseudoproperty

import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import com.ecommercedemo.userservice.persistence.pseudoproperty.IPseudoPropertyAdapter
import com.ecommercedemo.userservice.persistence.user.UserAdapterPostgresql
import com.ecommercedemo.userservice.service.userinfo.UserInfoService
import org.springframework.stereotype.Service

@Service
class PseudoPropertyService(
    private val userInfoService: UserInfoService,
    private val userAdapterPostgresql: UserAdapterPostgresql,
    private val customPropertyAdapter: IPseudoPropertyAdapter,
) {
    fun getAllUserCustomProperties(): List<UserServicePseudoProperty> {
        return customPropertyAdapter.getAllUserCustomProperties()
    }

    fun getAllContactDataCustomProperties(): List<UserServicePseudoProperty> {
        return customPropertyAdapter.getAllContactDataCustomProperties()
    }

    fun getUserCustomPropertyByKey(key: String): UserServicePseudoProperty? {
        return customPropertyAdapter.getUserCustomPropertyByKey(key)
    }

    fun getContactDataCustomPropertyByKey(key: String): UserServicePseudoProperty? {
        return customPropertyAdapter.getContactDataCustomPropertyByKey(key)
    }

    fun deleteCustomPropertyByEntityAndKey(entity: String, key: String) {
        customPropertyAdapter.deleteCustomPropertyByEntityAndKey(entity, key)
    }

    fun addCustomPropertyToAllUsers(customProperty: UserServicePseudoProperty): UserServicePseudoProperty {
        val property = customPropertyAdapter.createCustomProperty(customProperty)
        userAdapterPostgresql.addCustomPropertyToAllUsers(property)
        return property
    }


    fun addCustomPropertyToAllContactData(customProperty: UserServicePseudoProperty): UserServicePseudoProperty {
        val property = customPropertyAdapter.createCustomProperty(customProperty)
        userInfoService.addCustomPropertyToAllContactData(property)
        return property
    }

    fun renameCustomProperty(entity: String, key: String, newKey: String) : UserServicePseudoProperty {
        val updated = customPropertyAdapter.renameCustomProperty(entity, key, newKey)
        when (entity) {
            User::class.simpleName -> userAdapterPostgresql.renameCustomPropertyForAllUsers(key, newKey)
            UserInfo::class.simpleName -> userInfoService.renameCustomPropertyForAllContactData(key, newKey)
        }
        return updated
    }

    fun existsByEntityAndKey(entity: String, key: String): Boolean {
        return customPropertyAdapter.existsByEntityAndKey(entity, key)
    }
}