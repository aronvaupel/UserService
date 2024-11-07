package com.ecommercedemo.userservice.service.userinfo

import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import com.ecommercedemo.userservice.persistence.userinfo.IUserInfoAdapter
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserInfoService(
    private val contactDataAdapter: IUserInfoAdapter
) {
    fun getAllContactData(): List<UserInfo> {
        return contactDataAdapter.getAllUserInfo()
    }

    fun getContactDataByUserId(id: UUID): UserInfo? {
        return contactDataAdapter.getUserInfoByUserId(id)
    }

    fun saveContactData(data: UserInfo): UserInfo {
        return contactDataAdapter.saveUserInfo(data)
    }

    fun updateContactDataByUserId(id: UUID, data: UserInfo): UserInfo? {
        return contactDataAdapter.updateUserInfoByUserId(id, data)
    }

    fun deleteContactDataByUserId(id: UUID) {
        contactDataAdapter.deleteUserInfoByUserId(id)
    }

    fun addCustomPropertyToAllContactData(customProperty: UserServicePseudoProperty): UserServicePseudoProperty {
        contactDataAdapter.addCustomPropertyToAllUserInfo(customProperty)
        return customProperty
    }

    fun renameCustomPropertyForAllContactData(key: String, newKey: String) {
        contactDataAdapter.renameCustomPropertyForAllUserInfo(key, newKey)
    }
}