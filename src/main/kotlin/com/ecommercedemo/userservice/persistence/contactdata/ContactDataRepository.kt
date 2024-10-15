package com.ecommercedemo.userservice.persistence.contactdata

import com.ecommercedemo.userservice.model.contactdata.ContactData
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

@Suppress("FunctionName")
interface ContactDataRepository : JpaRepository<ContactData, UUID> {
    fun getContactDataByUser_Id(id: UUID): ContactData

    @Modifying
    @Transactional
    @Query("UPDATE ContactData c SET c = :data WHERE c.user.id = :id")
    fun updateContactDataByUser_Id(@Param("id") id: UUID, @Param("data") data: ContactData): ContactData?

    @Modifying
    @Transactional
    @Query("DELETE FROM ContactData c WHERE c.user.id = :id")
    fun deleteContactDataByUser_Id(@Param("id") id: UUID)

    fun existsByEmail(email: String): Boolean
}