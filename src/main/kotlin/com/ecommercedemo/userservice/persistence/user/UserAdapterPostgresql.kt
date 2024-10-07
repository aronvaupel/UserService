package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserAdapterPostgresql(
    private val userRepository: UserRepository
) : IUserAdapter {
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

}