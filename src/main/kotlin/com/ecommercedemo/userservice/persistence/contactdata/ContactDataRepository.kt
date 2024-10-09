package com.ecommercedemo.userservice.persistence.contactdata

import com.ecommercedemo.userservice.model.contactdata.ContactData
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Suppress("FunctionName")
interface ContactDataRepository : JpaRepository<ContactData, UUID> {
    fun getContactDataByUser_Id(id: UUID): ContactData
    fun updateContactDataByUser_Id(id: UUID, data: ContactData): ContactData
    fun deleteContactDataByUser_Id(id: UUID)
    fun existsByEmail(email: String): Boolean
}