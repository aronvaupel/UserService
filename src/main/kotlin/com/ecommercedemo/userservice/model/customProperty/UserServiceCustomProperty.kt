package com.ecommercedemo.userservice.model.customProperty

import com.ecommercedemo.common.model.CustomProperty
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = UserServiceCustomProperty.STORAGE_NAME,
    uniqueConstraints = [UniqueConstraint(columnNames = ["entityClassName", "key"])]
)
open class UserServiceCustomProperty: CustomProperty() {
    companion object {
        const val STORAGE_NAME = "custom_properties"
    }
}