package com.ecommercedemo.userservice.persistence.pseudoproperty

import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty

interface IPseudoPropertyAdapter {
    fun save(property: UserServicePseudoProperty): UserServicePseudoProperty
    fun getAllUserPseudoProperties(): List<UserServicePseudoProperty>
    fun existsByEntityAndKey(entity: String, key: String): Boolean
    fun getAllUserInfoPseudoProperties(): List<UserServicePseudoProperty>
    fun getUserPseudoPropertyByKey(key: String): UserServicePseudoProperty?
    fun getUserInfoPseudoPropertyByKey(key: String): UserServicePseudoProperty?
    fun deletePseudoPropertyByEntityAndKey(entity: String, key: String)
    fun createPseudoProperty(property: UserServicePseudoProperty): UserServicePseudoProperty
    fun renamePseudoProperty(entity: String, key: String, newKey: String): UserServicePseudoProperty
}