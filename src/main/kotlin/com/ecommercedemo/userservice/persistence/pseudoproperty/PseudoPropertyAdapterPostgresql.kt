package com.ecommercedemo.userservice.persistence.pseudoproperty

import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import org.springframework.stereotype.Service

@Service
class PseudoPropertyAdapterPostgresql(
    private val pseudoPropertyRepository: PseudoPropertyRepository
) : IPseudoPropertyAdapter {

    override fun save(property: UserServicePseudoProperty): UserServicePseudoProperty {
        return pseudoPropertyRepository.save(property)
    }

    override fun getAllUserCustomProperties(): List<UserServicePseudoProperty> {
        return pseudoPropertyRepository.getAllByEntityClassName(User::class.simpleName!!)
    }

    override fun existsByEntityAndKey(entity: String, key: String): Boolean {
        return pseudoPropertyRepository.existsByEntityClassNameAndKey(entity, key)
    }

    override fun getAllContactDataCustomProperties(): List<UserServicePseudoProperty> {
        return pseudoPropertyRepository.getAllByEntityClassName(UserInfo::class.simpleName!!)
    }

    override fun getUserCustomPropertyByKey(key: String): UserServicePseudoProperty? {
        return pseudoPropertyRepository.getByEntityClassNameAndKey(User::class.simpleName!!, key)
    }

    override fun getContactDataCustomPropertyByKey(key: String): UserServicePseudoProperty? {
        return pseudoPropertyRepository.getByEntityClassNameAndKey(UserInfo::class.simpleName!!, key)
    }

    override fun deleteCustomPropertyByEntityAndKey(entity: String, key: String) {
        pseudoPropertyRepository.deleteByEntityClassNameAndKey(entity, key)
    }

    override fun createCustomProperty(property: UserServicePseudoProperty): UserServicePseudoProperty {
        pseudoPropertyRepository.getByEntityClassNameAndKey(property.entityClassName, property.key)?.let {
            throw IllegalArgumentException("Property with key ${property.key} already exists for entity ${property.entityClassName}")
        }
        return pseudoPropertyRepository.save(property)
    }

    override fun renameCustomProperty(entity: String, key: String, newKey: String) : UserServicePseudoProperty {
        if (pseudoPropertyRepository.existsByEntityClassNameAndKey(entity, newKey))
            throw IllegalArgumentException("Property with key $newKey already exists for entity $entity")
        return pseudoPropertyRepository.getByEntityClassNameAndKey(entity, key)?.let {
            it.key = newKey
            pseudoPropertyRepository.save(it)
            //Todo: Send events
        } ?: throw IllegalArgumentException("Property with key $key does not exist for entity $entity")
    }

}