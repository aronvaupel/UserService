package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.common.model.PseudoProperty
import com.ecommercedemo.common.util.filter.QueryParams
import com.ecommercedemo.userservice.model.user.User
import java.util.*

interface IUserAdapter {
    fun getAllUsers(): List<User>
    fun saveUser(user: User): User
    fun getUserById(id: UUID): User
    fun deleteUser(id: UUID)
    fun getUserByUsername(username: String): User
    fun getUsers(queryParams: QueryParams<User>): List<User>
    fun addPseudoPropertyToAllUsers(property: PseudoProperty)
    fun removePseudoPropertyFromAllUsers(property: PseudoProperty)
    fun renamePseudoPropertyForAllUsers(key: String, newKey: String)
}