package com.ecommercedemo.userservice.persistence.userinfo

import com.ecommercedemo.common.model.CustomProperty
import com.ecommercedemo.common.model.embedded.CustomPropertyData
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

    override fun addCustomPropertyToAllUserInfo(customProperty: UserServicePseudoProperty) {
        val userInfo = userInfoRepository.findAll()
        userInfo.forEach { data ->
            val exists = data.customProperties.any { it.key == customProperty.key }
            if (exists) throw IllegalArgumentException("Property with key ${customProperty.key} already exists")
            data.customProperties.add(
                CustomPropertyData.serialize(
                    entity = UserInfo::class.simpleName!!,
                    key = customProperty.key,
                    value = null
                )
            )
            userInfoRepository.save(data)
        }
    }

    override fun removeCustomPropertyFromAllUserInfo(property: CustomProperty) {
        val isPresent = pseudoPropertyAdapterPostgresql.existsByEntityAndKey(UserInfo::class.simpleName!!, property.key)
        if (!isPresent) throw IllegalArgumentException("Property with key ${property.key} does not exist")
        val contactData = userInfoRepository.findAll()
        contactData.forEach { data ->
            data.customProperties.removeIf { it.key == property.key }
            userInfoRepository.save(data)
        }
    }

    override fun renameCustomPropertyForAllUserInfo(key: String, newKey: String) {
        val allContactData = getAllUserInfo()
        val exists = allContactData.any { data -> data.customProperties.any { it.key == key } }
        if (!exists) throw IllegalArgumentException("Property with key $key does not exist")
        val newKeyExists = allContactData.any { data -> data.customProperties.any { it.key == newKey } }
        if (newKeyExists) throw IllegalArgumentException("Property with key $newKey already exists")
        allContactData.forEach { user ->
            user.customProperties.forEach {
                if (it.key == key) {
                    it.key = newKey
                }
            }
            userInfoRepository.save(user)
        }
    }
}