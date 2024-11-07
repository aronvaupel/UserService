package com.ecommercedemo.userservice.persistence.customProperty

import com.ecommercedemo.userservice.model.contactdata.UserInfo
import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service

@Service
class CustomPropertyAdapterPostgresql(
    private val customPropertyRepository: CustomPropertyRepository
) : ICustomPropertyAdapter {

    override fun save(property: UserServicePseudoProperty): UserServicePseudoProperty {
        return customPropertyRepository.save(property)
    }

    override fun getAllUserCustomProperties(): List<UserServicePseudoProperty> {
        return customPropertyRepository.getAllByEntityClassName(User::class.simpleName!!)
    }

    override fun existsByEntityAndKey(entity: String, key: String): Boolean {
        return customPropertyRepository.existsByEntityClassNameAndKey(entity, key)
    }

    override fun getAllContactDataCustomProperties(): List<UserServicePseudoProperty> {
        return customPropertyRepository.getAllByEntityClassName(UserInfo::class.simpleName!!)
    }

    override fun getUserCustomPropertyByKey(key: String): UserServicePseudoProperty? {
        return customPropertyRepository.getByEntityClassNameAndKey(User::class.simpleName!!, key)
    }

    override fun getContactDataCustomPropertyByKey(key: String): UserServicePseudoProperty? {
        return customPropertyRepository.getByEntityClassNameAndKey(UserInfo::class.simpleName!!, key)
    }

    override fun deleteCustomPropertyByEntityAndKey(entity: String, key: String) {
        customPropertyRepository.deleteByEntityClassNameAndKey(entity, key)
    }

    override fun createCustomProperty(property: UserServicePseudoProperty): UserServicePseudoProperty {
        customPropertyRepository.getByEntityClassNameAndKey(property.entityClassName, property.key)?.let {
            throw IllegalArgumentException("Property with key ${property.key} already exists for entity ${property.entityClassName}")
        }
        return customPropertyRepository.save(property)
    }

    override fun renameCustomProperty(entity: String, key: String, newKey: String) : UserServicePseudoProperty {
        if (customPropertyRepository.existsByEntityClassNameAndKey(entity, newKey))
            throw IllegalArgumentException("Property with key $newKey already exists for entity $entity")
        return customPropertyRepository.getByEntityClassNameAndKey(entity, key)?.let {
            it.key = newKey
            customPropertyRepository.save(it)
            //Todo: Send events
        } ?: throw IllegalArgumentException("Property with key $key does not exist for entity $entity")
    }

}