package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.common.model.PseudoProperty
import com.ecommercedemo.common.util.search.Retriever
import com.ecommercedemo.common.util.search.dto.SearchRequest
import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserAdapterPostgresql(
    private val retriever: Retriever,
    private val userRepository: UserRepository,
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

    override fun deleteUser(id: UUID) {
        userRepository.deleteById(id)
    }

    override fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
    }

    override fun getUsers(searchRequest: SearchRequest): List<User> {
        return retriever.executeSearch(searchRequest, User::class)
    }

    override fun addPseudoPropertyToAllUsers(property: PseudoProperty) {
        val users = userRepository.findAll()
        users.forEach { user ->
            val exists = user.pseudoProperties.any { it.key == property.key }
            if (exists) throw IllegalArgumentException("Property with key ${property.key} already exists")
            user.pseudoProperties[property.key] = property.value as Any
            userRepository.save(user)
        }
    }

    override fun removePseudoPropertyFromAllUsers(property: PseudoProperty) {
        val users = userRepository.findAll()
        val exists = users.any { user -> user.pseudoProperties.any { it.key == property.key } }
        if (!exists) throw IllegalArgumentException("Property with key ${property.key} does not exist")
        users.forEach { user ->
            user.pseudoProperties.remove(property.key)
            userRepository.save(user)
        }
    }

    override fun renamePseudoPropertyForAllUsers(key: String, newKey: String)  {
        val users = userRepository.findAll()
        val exists = users.any { user -> user.pseudoProperties.any { it.key == key } }
        if (!exists) throw IllegalArgumentException("Property with key $key does not exist")
        val newKeyExists = users.any { user -> user.pseudoProperties.any { it.key == newKey } }
        if (newKeyExists) throw IllegalArgumentException("Property with key $newKey already exists")
        users.forEach { user ->
            if (user.pseudoProperties.containsKey(key)) {
                user.pseudoProperties[newKey] = user.pseudoProperties.remove(key)!!
            }
            userRepository.save(user)
        }
    }

}