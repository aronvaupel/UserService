package com.ecommercedemo.userservice.persistence.userinfo

import com.ecommercedemo.common.model.PseudoProperty
import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import java.util.*

interface IUserInfoAdapter {
    fun getAllUserInfo(): List<UserInfo>
    fun saveUserInfo(data: UserInfo): UserInfo
    fun getUserInfoByUserId(id:UUID): UserInfo?
    fun updateUserInfoByUserId(id:UUID, data: UserInfo): UserInfo?
    fun deleteUserInfoByUserId(id: UUID)
    fun existsByMail(email: String): Boolean
    fun addPseudoPropertyToAllUserInfo(pseudoProperty: UserServicePseudoProperty)
    fun removePseudoPropertyFromAllUserInfo(property: PseudoProperty)
    fun renamePseudoPropertyForAllUserInfo(key: String, newKey: String)
}