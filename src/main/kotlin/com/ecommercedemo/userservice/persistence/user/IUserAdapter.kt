package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.common.application.search.dto.SearchRequest
import com.ecommercedemo.userservice.model.user.User
import java.util.*

interface IUserAdapter {
    fun getAllUsers(): List<User>
    fun saveUser(user: User): User
    fun getUserById(id: UUID): User
    fun deleteUser(id: UUID)
    fun getUsers(searchRequest: SearchRequest): List<User>

}