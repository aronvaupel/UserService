package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.common.application.search.Retriever
import com.ecommercedemo.common.application.search.dto.SearchRequest
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

    override fun getUsers(searchRequest: SearchRequest): List<User> {
        return retriever.executeSearch(searchRequest, User::class)
    }

}