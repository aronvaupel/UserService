package com.ecommercedemo.userservice.persistence.userinfo

import com.ecommercedemo.userservice.model.userinfo.UserInfo
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

@Suppress("FunctionName")
interface UserInfoRepository : JpaRepository<UserInfo, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE UserInfo u SET u = :data WHERE u.user.id = :id")
    fun updateUserInfoByUser_Id(@Param("id") id: UUID, @Param("data") data: UserInfo): UserInfo?

    @Modifying
    @Transactional
    @Query("DELETE FROM UserInfo u WHERE u.user.id = :id")
    fun deleteUserInfoByUser_Id(@Param("id") id: UUID)

    fun existsByEmail(email: String): Boolean
}