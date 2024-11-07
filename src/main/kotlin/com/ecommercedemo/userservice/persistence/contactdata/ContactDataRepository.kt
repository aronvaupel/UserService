package com.ecommercedemo.userservice.persistence.contactdata

import com.ecommercedemo.userservice.model.contactdata.UserInfo
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

@Suppress("FunctionName")
interface ContactDataRepository : JpaRepository<UserInfo, UUID> {
    fun getContactDataByUser_Id(id: UUID): UserInfo

    @Modifying
    @Transactional
    @Query("UPDATE UserInfo c SET c = :data WHERE c.user.id = :id")
    fun updateContactDataByUser_Id(@Param("id") id: UUID, @Param("data") data: UserInfo): UserInfo?

    @Modifying
    @Transactional
    @Query("DELETE FROM UserInfo c WHERE c.user.id = :id")
    fun deleteContactDataByUser_Id(@Param("id") id: UUID)

    fun existsByEmail(email: String): Boolean
}