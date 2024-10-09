package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.common.model.CustomProperty
import com.ecommercedemo.common.model.embedded.CustomPropertyData
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.service.customproperty.CustomPropertyService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserAdapterPostgresql(
    private val userRepository: UserRepository,
    private val customPropertyService: CustomPropertyService
) : IUserAdapter {

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun saveUser(user: User): User {
        return userRepository.save(user)
    }

    override fun getUserById(id: UUID): User {
        return userRepository.findById(id).orElseThrow()
    }

    override fun updateUser(id: UUID): User {
        return userRepository.findById(id).orElseThrow()
    }

    override fun deleteUser(id: UUID) {
        userRepository.deleteById(id)
    }

    override fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
    }

    override fun getUsers(ids: List<UUID>): List<User> {
        return userRepository.findAllById(ids)
    }

    override fun addCustomPropertyToAllUsers(property: CustomProperty<*>) {
        val users = userRepository.findAll()
        users.forEach { user ->
            val exists = user.customProperties.any { it.key == property.key }
            if (exists) throw IllegalArgumentException("Property with key ${property.key} already exists")
            user.customProperties.add(CustomPropertyData.serialize(
                entity = User::class.simpleName!!,
                key = property.key,
                value = null
            ))
            userRepository.save(user)
        }
    }

    override fun removeCustomPropertyFromAllUsers(property: CustomProperty<*>) {
        val users = userRepository.findAll()
        val exists = users.any { user -> user.customProperties.any { it.key == property.key } }
        if (!exists) throw IllegalArgumentException("Property with key ${property.key} does not exist")
        users.forEach { user ->
            user.customProperties.removeIf { it.key == property.key }
            userRepository.save(user)
        }
    }

    override fun renameCustomPropertyForAllUsers(key: String, newKey: String)  {
        val users = userRepository.findAll()
        val exists = users.any { user -> user.customProperties.any { it.key == key } }
        if (!exists) throw IllegalArgumentException("Property with key $key does not exist")
        val newKeyExists = users.any { user -> user.customProperties.any { it.key == newKey } }
        if (newKeyExists) throw IllegalArgumentException("Property with key $newKey already exists")
        users.forEach { user ->
            user.customProperties.forEach {
                if (it.key == key) {
                    it.key = newKey
                }
            }
            userRepository.save(user)
        }
    }

}