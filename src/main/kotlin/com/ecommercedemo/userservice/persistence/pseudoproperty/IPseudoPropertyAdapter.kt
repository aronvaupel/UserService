package com.ecommercedemo.userservice.persistence.pseudoproperty

import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty

interface IPseudoPropertyAdapter {
    fun save(property: UserServicePseudoProperty): UserServicePseudoProperty
    fun getAllUserCustomProperties(): List<UserServicePseudoProperty>
    fun existsByEntityAndKey(entity: String, key: String): Boolean
    fun getAllContactDataCustomProperties(): List<UserServicePseudoProperty>
    fun getUserCustomPropertyByKey(key: String): UserServicePseudoProperty?
    fun getContactDataCustomPropertyByKey(key: String): UserServicePseudoProperty?
    fun deleteCustomPropertyByEntityAndKey(entity: String, key: String)
    fun createCustomProperty(property: UserServicePseudoProperty): UserServicePseudoProperty
    fun renameCustomProperty(entity: String, key: String, newKey: String): UserServicePseudoProperty
}