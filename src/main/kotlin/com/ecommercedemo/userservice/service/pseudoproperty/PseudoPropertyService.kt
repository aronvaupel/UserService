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
    private val pseudoPropertyAdapter: IPseudoPropertyAdapter,
) {
    fun getAllUserPseudoProperties(): List<UserServicePseudoProperty> {
        return pseudoPropertyAdapter.getAllUserPseudoProperties()
    }

    fun getAllUserInfoPseudoProperties(): List<UserServicePseudoProperty> {
        return pseudoPropertyAdapter.getAllUserInfoPseudoProperties()
    }

    fun getUserPseudoPropertyByKey(key: String): UserServicePseudoProperty? {
        return pseudoPropertyAdapter.getUserPseudoPropertyByKey(key)
    }

    fun getUserInfoPseudoPropertyByKey(key: String): UserServicePseudoProperty? {
        return pseudoPropertyAdapter.getUserInfoPseudoPropertyByKey(key)
    }

    fun deletePseudoPropertyByEntityAndKey(entity: String, key: String) {
        pseudoPropertyAdapter.deletePseudoPropertyByEntityAndKey(entity, key)
    }

    fun addPseudoPropertyToAllUsers(pseudoProperty: UserServicePseudoProperty): UserServicePseudoProperty {
        val property = pseudoPropertyAdapter.createPseudoProperty(pseudoProperty)
        userAdapterPostgresql.addPseudoPropertyToAllUsers(property)
        return property
    }


    fun addPseudoPropertyToAllUserInfo(pseudoProperty: UserServicePseudoProperty): UserServicePseudoProperty {
        val property = pseudoPropertyAdapter.createPseudoProperty(pseudoProperty)
        userInfoService.addPseudoPropertyToAllUserInfo(property)
        return property
    }

    fun renamePseudoProperty(entity: String, key: String, newKey: String) : UserServicePseudoProperty {
        val updated = pseudoPropertyAdapter.renamePseudoProperty(entity, key, newKey)
        when (entity) {
            User::class.simpleName -> userAdapterPostgresql.renamePseudoPropertyForAllUsers(key, newKey)
            UserInfo::class.simpleName -> userInfoService.renamePseudoPropertyForAllUserInfo(key, newKey)
        }
        return updated
    }

    fun existsByEntityAndKey(entity: String, key: String): Boolean {
        return pseudoPropertyAdapter.existsByEntityAndKey(entity, key)
    }
}