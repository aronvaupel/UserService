package com.ecommercedemo.userservice.persistence.customProperty

import com.ecommercedemo.userservice.model.contactdata.ContactData
import com.ecommercedemo.userservice.model.customProperty.UserServiceCustomProperty
import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service

@Service
class CustomPropertyAdapterPostgresql(
    private val customPropertyRepository: CustomPropertyRepository<Any>
) : ICustomPropertyAdapter {

    override fun save(property: UserServiceCustomProperty<*>): UserServiceCustomProperty<*> {
        return customPropertyRepository.save(property)
    }

    override fun getAllUserCustomProperties(): List<UserServiceCustomProperty<*>> {
        return customPropertyRepository.getAllByEntityClassName(User::class.simpleName!!)
    }

    override fun existsByEntityAndKey(entity: String, key: String): Boolean {
        return customPropertyRepository.existsByEntityClassNameAndKey(entity, key)
    }

    override fun getAllContactDataCustomProperties(): List<UserServiceCustomProperty<*>> {
        return customPropertyRepository.getAllByEntityClassName(ContactData::class.simpleName!!)
    }

    override fun getUserCustomPropertyByKey(key: String): UserServiceCustomProperty<*>? {
        return customPropertyRepository.getByEntityClassNameAndKey(User::class.simpleName!!, key)
    }

    override fun getContactDataCustomPropertyByKey(key: String): UserServiceCustomProperty<*>? {
        return customPropertyRepository.getByEntityClassNameAndKey(ContactData::class.simpleName!!, key)
    }

    override fun deleteCustomPropertyByEntityAndKey(entity: String, key: String) {
        customPropertyRepository.deleteByEntityClassNameAndKey(entity, key)
    }

    override fun createCustomProperty(property: UserServiceCustomProperty<*>): UserServiceCustomProperty<*> {
        customPropertyRepository.getByEntityClassNameAndKey(property.entityClassName, property.key)?.let {
            throw IllegalArgumentException("Property with key ${property.key} already exists for entity ${property.entityClassName}")
        }
        return customPropertyRepository.save(property)
    }

    override fun renameCustomProperty(entity: String, key: String, newKey: String) : UserServiceCustomProperty<*> {
        if (customPropertyRepository.existsByEntityClassNameAndKey(entity, newKey))
            throw IllegalArgumentException("Property with key $newKey already exists for entity $entity")
        return customPropertyRepository.getByEntityClassNameAndKey(entity, key)?.let {
            it.key = newKey
            customPropertyRepository.save(it)
            //Todo: Send events
        } ?: throw IllegalArgumentException("Property with key $key does not exist for entity $entity")
    }

}