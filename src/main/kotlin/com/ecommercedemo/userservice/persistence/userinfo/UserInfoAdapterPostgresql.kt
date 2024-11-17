package com.ecommercedemo.userservice.persistence.userinfo

import com.ecommercedemo.userservice.model.userinfo.UserInfo
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserInfoAdapterPostgresql(
    private val userInfoRepository: UserInfoRepository,
) : IUserInfoAdapter {

    override fun getAllUserInfo(): List<UserInfo> {
        return userInfoRepository.findAll()
    }

    override fun saveUserInfo(data: UserInfo): UserInfo {
        return userInfoRepository.save(data)
    }

    override fun getUserInfoByUserId(id: UUID): UserInfo? {
        return userInfoRepository.findById(id).orElseThrow()
    }

    override fun updateUserInfoByUserId(id: UUID, data: UserInfo): UserInfo? {
        return userInfoRepository.updateUserInfoByUser_Id(id, data)

    }

    override fun deleteUserInfoByUserId(id: UUID) {
        userInfoRepository.deleteUserInfoByUser_Id(id)
    }

    override fun existsByMail(email: String): Boolean {
        return userInfoRepository.existsByEmail(email)
    }
}