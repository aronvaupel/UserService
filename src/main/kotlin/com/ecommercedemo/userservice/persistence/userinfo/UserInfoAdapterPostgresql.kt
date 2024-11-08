package com.ecommercedemo.userservice.persistence.userinfo

import com.ecommercedemo.common.model.PseudoProperty
import com.ecommercedemo.common.model.embedded.PseudoPropertyData
import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import com.ecommercedemo.userservice.persistence.pseudoproperty.PseudoPropertyAdapterPostgresql
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserInfoAdapterPostgresql(
    private val userInfoRepository: UserInfoRepository,
    private val pseudoPropertyAdapterPostgresql: PseudoPropertyAdapterPostgresql,
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

    override fun addPseudoPropertyToAllUserInfo(pseudoProperty: UserServicePseudoProperty) {
        val userInfo = userInfoRepository.findAll()
        userInfo.forEach { data ->
            val exists = data.pseudoProperties.any { it.key == pseudoProperty.key }
            if (exists) throw IllegalArgumentException("Property with key ${pseudoProperty.key} already exists")
            data.pseudoProperties.add(
                PseudoPropertyData.serialize(
                    entity = UserInfo::class.simpleName!!,
                    key = pseudoProperty.key,
                    value = null
                )
            )
            userInfoRepository.save(data)
        }
    }

    override fun removePseudoPropertyFromAllUserInfo(property: PseudoProperty) {
        val isPresent = pseudoPropertyAdapterPostgresql.existsByEntityAndKey(UserInfo::class.simpleName!!, property.key)
        if (!isPresent) throw IllegalArgumentException("Property with key ${property.key} does not exist")
        val userInfo = userInfoRepository.findAll()
        userInfo.forEach { data ->
            data.pseudoProperties.removeIf { it.key == property.key }
            userInfoRepository.save(data)
        }
    }

    override fun renamePseudoPropertyForAllUserInfo(key: String, newKey: String) {
        val allUserInfo = getAllUserInfo()
        val exists = allUserInfo.any { data -> data.pseudoProperties.any { it.key == key } }
        if (!exists) throw IllegalArgumentException("Property with key $key does not exist")
        val newKeyExists = allUserInfo.any { data -> data.pseudoProperties.any { it.key == newKey } }
        if (newKeyExists) throw IllegalArgumentException("Property with key $newKey already exists")
        allUserInfo.forEach { user ->
            user.pseudoProperties.forEach {
                if (it.key == key) {
                    it.key = newKey
                }
            }
            userInfoRepository.save(user)
        }
    }
}