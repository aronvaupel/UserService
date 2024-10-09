package com.ecommercedemo.userservice.persistence.customProperty

import com.ecommercedemo.userservice.model.customProperty.UserServiceCustomProperty

interface ICustomPropertyAdapter {
    fun save(property: UserServiceCustomProperty<*>): UserServiceCustomProperty<*>
    fun getAllUserCustomProperties(): List<UserServiceCustomProperty<*>>
    fun existsByEntityAndKey(entity: String, key: String): Boolean
    fun getAllContactDataCustomProperties(): List<UserServiceCustomProperty<*>>
    fun getUserCustomPropertyByKey(key: String): UserServiceCustomProperty<*>?
    fun getContactDataCustomPropertyByKey(key: String): UserServiceCustomProperty<*>?
    fun deleteCustomPropertyByEntityAndKey(entity: String, key: String)
    fun createCustomProperty(property: UserServiceCustomProperty<*>): UserServiceCustomProperty<*>
    fun renameCustomProperty(entity: String, key: String, newKey: String): UserServiceCustomProperty<*>
}