package com.ecommercedemo.userservice.persistence.contactdata

import com.ecommercedemo.userservice.model.contactdata.ContactData
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ContactDataRepository : JpaRepository<ContactData, UUID> {
    fun existsByEmail(email: String): Boolean
}