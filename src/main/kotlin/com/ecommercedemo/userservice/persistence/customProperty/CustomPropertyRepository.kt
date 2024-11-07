package com.ecommercedemo.userservice.persistence.customProperty

import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CustomPropertyRepository: JpaRepository<UserServicePseudoProperty, UUID> {
    fun getAllByEntityClassName(entityClassName: String): List<UserServicePseudoProperty>
    fun getByEntityClassNameAndKey(entityClassName: String, key: String): UserServicePseudoProperty?
    fun deleteByEntityClassNameAndKey(entityClassName: String, key: String)
    fun existsByEntityClassNameAndKey(entityClassName: String, key: String): Boolean
}