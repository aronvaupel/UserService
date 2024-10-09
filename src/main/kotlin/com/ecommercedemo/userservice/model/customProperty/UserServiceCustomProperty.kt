package com.ecommercedemo.userservice.model.customProperty

import com.ecommercedemo.common.model.CustomProperty
import jakarta.persistence.Table
import java.util.*

@Table(name = UserServiceCustomProperty.STORAGE_NAME)
class UserServiceCustomProperty< V: Any>(
    id: UUID,
    entityClassName: String,
    key: String,
    value: V
) : CustomProperty< V>(id, entityClassName, key, value) {
    companion object {
        const val STORAGE_NAME = "UserServiceCustomProperty"
    }
}