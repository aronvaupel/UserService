package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.userservice.model.user.User
import java.util.*

interface IUserAdapter {
    fun saveUser(user: User): User
    fun getUserById(id: UUID): User
    fun updateUser(id: UUID): User
    fun deleteUser(id: UUID)
    fun getUserByUsername(username: String): User
    fun getUsers(ids: List<UUID>): List<User>
}