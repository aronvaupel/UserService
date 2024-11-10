package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.common.model.PseudoProperty
import com.ecommercedemo.common.model.embedded.PseudoPropertyData
import com.ecommercedemo.common.util.filter.QueryBuilder
import com.ecommercedemo.common.util.filter.QueryParams
import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserAdapterPostgresql(
    private val queryBuilder: QueryBuilder<User>,
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

    override fun getUsers(queryParams: QueryParams<User>): List<User> {
        return queryBuilder.buildQuery(User::class, queryParams)
    }

    override fun addPseudoPropertyToAllUsers(property: PseudoProperty) {
        val users = userRepository.findAll()
        users.forEach { user ->
            val exists = user.pseudoProperties.any { it.key == property.key }
            if (exists) throw IllegalArgumentException("Property with key ${property.key} already exists")
            user.pseudoProperties.add(PseudoPropertyData.serialize(
                entity = User::class.simpleName!!,
                key = property.key,
                value = null
            ))
            userRepository.save(user)
        }
    }

    override fun removePseudoPropertyFromAllUsers(property: PseudoProperty) {
        val users = userRepository.findAll()
        val exists = users.any { user -> user.pseudoProperties.any { it.key == property.key } }
        if (!exists) throw IllegalArgumentException("Property with key ${property.key} does not exist")
        users.forEach { user ->
            user.pseudoProperties.removeIf { it.key == property.key }
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
            user.pseudoProperties.forEach {
                if (it.key == key) {
                    it.key = newKey
                }
            }
            userRepository.save(user)
        }
    }

}