package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.userservice.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String): User
}