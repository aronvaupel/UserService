package com.ecommercedemo.userservice.service.user

import com.ecommercedemo.common.model.CustomProperty
import com.ecommercedemo.userservice.config.env.EnvironmentConfig
import com.ecommercedemo.userservice.dto.user.RegisterUserDto
import com.ecommercedemo.userservice.dto.user.UserDto
import com.ecommercedemo.userservice.model.contactdata.ContactData
import com.ecommercedemo.userservice.model.customProperty.UserServiceCustomProperty
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.persistence.contactdata.IContactDataAdapter
import com.ecommercedemo.userservice.persistence.user.IUserAdapter
import com.ecommercedemo.userservice.validation.userrole.UserRole
import io.github.cdimascio.dotenv.Dotenv
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Service
class UserService(
    private val contactDataPersistence: IContactDataAdapter,
    envConfig: EnvironmentConfig,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val userAdapter: IUserAdapter,
) {

    private val dotenv: Dotenv? = if (envConfig.isLocalEnv) Dotenv.load() else null
    private val guestPassword = dotenv?.get("GUEST_PASSWORD") ?: System.getenv("GUEST_PASSWORD")

    private val scheduler = Executors.newScheduledThreadPool(1)

    fun getUser(id: UUID) = userAdapter.getUserById(id)
    fun saveUser(user: User) = userAdapter.saveUser(user)
    fun updateUser(user: User) = userAdapter.updateUser(user.id)
    fun deleteUser(id: UUID) = userAdapter.deleteUser(id)
    fun getUserByUsername(username: String) = userAdapter.getUserByUsername(username)
    fun getUsers(ids: List<UUID>) = userAdapter.getUsers(ids)

    fun registerGuest(): UserDto {
        val guest = userAdapter.saveUser(
            User(
                username = "guest_${System.currentTimeMillis()}",
                _password = guestPassword,
                userRole = UserRole.GUEST,
                contactData = null,
                gender = null,
                dateOfBirth = null
            )
        ).toDto()
        scheduleDeletion(guest.id, 15 * 60 * 1000)
        return guest
    }


    fun registerUser(dto: RegisterUserDto): UserDto {
        if (emailIsUnique(dto.email)) {
            throw IllegalArgumentException("Email already exists")
        }
        val contactData = ContactData(
            email = dto.email,
            firstName = dto.firstName,
            lastName = dto.lastName,
        )
        val user = userAdapter.saveUser(
            User(
                username = dto.userName,
                _password = dto.password,
                userRole = UserRole.REGISTERED_USER,
                contactData = ContactData(
                    email = dto.email,
                    firstName = dto.firstName,
                    lastName = dto.lastName,
                ),
                gender = null,
                dateOfBirth = null,
            )
        )
        contactDataPersistence.saveContactData(contactData)
        //TODO: Send email to user and send events for  User and ContactData
        return user.toDto()
    }

    fun importUsersFromExcel(file: MultipartFile, sheet: String, rows: Int, evaluationColumn: String) {
        // Implement the logic to import users from an Excel file
        // This is a placeholder implementation
        println("Importing users from Excel file")
    }

    fun exportUsersToExcel(filter: String?, contactDataProperties: List<String>?): ByteArray {
        // Implement the logic to export users to an Excel file
        // This is a placeholder implementation
        println("Exporting users to Excel file")
        return ByteArray(0)
    }

    fun exportUsersToPdf(filter: String?): ByteArray {
        // Implement the logic to export users to a PDF file
        // This is a placeholder implementation
        println("Exporting users to PDF file")
        return ByteArray(0)
    }

    fun emailIsUnique(email: String): Boolean {
        return contactDataPersistence.existsByMail(email)
    }

    fun updateLastActivity(userId: UUID, lastActivityTimestamp: Long) {
        val user = getUser(userId)
        val lastActivity = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastActivityTimestamp), ZoneId.systemDefault())
        user.lastActive = lastActivity
        saveUser(user)
        redisTemplate.opsForValue().set("user:lastActivity:$userId", lastActivityTimestamp)
    }

    fun scheduleDeletion(userId: UUID, delay: Long) {
        scheduler.schedule({
            userAdapter.deleteUser(userId)
        }, delay, TimeUnit.MILLISECONDS)
    }

    fun scheduleInactivityCheck(id: UUID) {
        scheduler.schedule({
            val lastActivityKey = "user:lastActivity:$id"
            val lastActivityTimestamp = redisTemplate.opsForValue().get(lastActivityKey) as Long?
            if (lastActivityTimestamp != null) {
                val lastActivity =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(lastActivityTimestamp), ZoneId.systemDefault())
                if (lastActivity.plusMinutes(15).isBefore(LocalDateTime.now())) {
                    val user = userAdapter.getUserById(id)
                    if (user.userRole == UserRole.GUEST) {
                        userAdapter.deleteUser(id)
                    } else {
                        logoutUser(id)
                    }
                }
            }
        }, 15, TimeUnit.MINUTES)
    }

    fun logoutUser(userId: UUID) {
        // Implement the logic to log out the user, e.g., invalidate tokens, clear sessions, etc.
        // This is a placeholder implementation
        println("Logging out user with ID: $userId")
    }

    fun updatePassword(userId: UUID, newPassword: String) {
        val user = getUser(userId)
        user.password = newPassword
        saveUser(user)
    }

    fun updateUsername(userId: UUID, newUsername: String) {
        val user = getUser(userId)
        user.username = newUsername
        saveUser(user)
    }

    fun addCustomPropertyToAllUsers(customProperty: UserServiceCustomProperty) : CustomProperty {
        userAdapter.addCustomPropertyToAllUsers(customProperty)
        return customProperty
    }

    fun renameCustomPropertyForAllUsers(key: String, newKey: String)  {
        return userAdapter.renameCustomPropertyForAllUsers(key, newKey)
    }


}