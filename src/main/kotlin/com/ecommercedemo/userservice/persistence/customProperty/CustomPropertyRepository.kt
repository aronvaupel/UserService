package com.ecommercedemo.userservice.persistence.customProperty

import com.ecommercedemo.userservice.model.customProperty.UserServiceCustomProperty
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CustomPropertyRepository<E : Any>: JpaRepository<UserServiceCustomProperty<*>, UUID> {
    fun getAllByEntityClassName(entityClassName: String): List<UserServiceCustomProperty<*>>
    fun getByEntityClassNameAndKey(entityClassName: String, key: String): UserServiceCustomProperty<*>?
    fun deleteByEntityClassNameAndKey(entityClassName: String, key: String)
    fun existsByEntityClassNameAndKey(entityClassName: String, key: String): Boolean
}