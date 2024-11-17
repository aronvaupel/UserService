package com.ecommercedemo.userservice.persistence.userinfo

import com.ecommercedemo.userservice.model.userinfo.UserInfo
import java.util.*

interface IUserInfoAdapter {
    fun getAllUserInfo(): List<UserInfo>
    fun saveUserInfo(data: UserInfo): UserInfo
    fun getUserInfoByUserId(id:UUID): UserInfo?
    fun updateUserInfoByUserId(id:UUID, data: UserInfo): UserInfo?
    fun deleteUserInfoByUserId(id: UUID)
    fun existsByMail(email: String): Boolean
}