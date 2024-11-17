package com.ecommercedemo.userservice.service.userinfo

import com.ecommercedemo.userservice.model.userinfo.UserInfo
import com.ecommercedemo.userservice.persistence.userinfo.IUserInfoAdapter
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserInfoService(
    private val userInfoAdapter: IUserInfoAdapter
) {
    fun getAllUserInfo(): List<UserInfo> {
        return userInfoAdapter.getAllUserInfo()
    }

    fun getUserInfoByUserId(id: UUID): UserInfo? {
        return userInfoAdapter.getUserInfoByUserId(id)
    }

    fun saveUserInfo(data: UserInfo): UserInfo {
        return userInfoAdapter.saveUserInfo(data)
    }

    fun updateUserInfoByUserId(id: UUID, data: UserInfo): UserInfo? {
        return userInfoAdapter.updateUserInfoByUserId(id, data)
    }

    fun deleteUserInfoByUserId(id: UUID) {
        userInfoAdapter.deleteUserInfoByUserId(id)
    }
}