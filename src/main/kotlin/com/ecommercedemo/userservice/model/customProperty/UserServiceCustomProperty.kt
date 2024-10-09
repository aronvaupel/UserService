package com.ecommercedemo.userservice.model.customProperty

import com.ecommercedemo.common.model.CustomProperty
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = UserServiceCustomProperty.STORAGE_NAME)
open class UserServiceCustomProperty< V: Any>(
    id: UUID,
    entityClassName: String,
    key: String,
    value: V
) : CustomProperty< V>(id, entityClassName, key, value) {
    companion object {
        const val STORAGE_NAME = "custom_properties"
    }
}