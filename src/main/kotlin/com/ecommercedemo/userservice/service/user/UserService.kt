package com.ecommercedemo.userservice.service.user

import com.ecommercedemo.common.kafka.EntityEventProducer
import com.ecommercedemo.common.kafka.EntityEventType
import com.ecommercedemo.common.model.PseudoProperty
import com.ecommercedemo.common.security.EnvConfig
import com.ecommercedemo.common.util.EntityChangeTracker
import com.ecommercedemo.common.util.search.dto.SearchRequest
import com.ecommercedemo.common.validation.userrole.UserRole
import com.ecommercedemo.userservice.dto.user.UserRegisterDto
import com.ecommercedemo.userservice.dto.user.UserResponseDto
import com.ecommercedemo.userservice.dto.user.UserUpdateDto
import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import com.ecommercedemo.userservice.persistence.user.IUserAdapter
import com.ecommercedemo.userservice.persistence.userinfo.IUserInfoAdapter
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
    envConfig: EnvConfig,
    private val eventProducer: EntityEventProducer,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val userInfoAdapter: IUserInfoAdapter,
    private val userAdapter: IUserAdapter,
) {

    private val dotenv: Dotenv? = if (envConfig.isLocalEnv) Dotenv.load() else null
    private val guestPassword = dotenv?.get("GUEST_PASSWORD") ?: System.getenv("GUEST_PASSWORD")

    private val scheduler = Executors.newScheduledThreadPool(1)

    fun getUser(id: UUID) = userAdapter.getUserById(id)
    fun saveUser(user: User) = userAdapter.saveUser(user)

    fun updateUser(dto: UserUpdateDto): UserResponseDto {
        val user = getUser(dto.id)
        val updatedUser = user.copy(
            username = dto.username ?: user.username,
        ).apply {
            if (dto.password != null) {
                password = dto.password
            }
        }
        val changes =
            EntityChangeTracker<User>().getChangedProperties(user, updatedUser).filterKeys { it != "_password" }
                .toMutableMap()

        saveUser(updatedUser)
        eventProducer.emit(
            User::class.java, updatedUser.id, EntityEventType.UPDATE, changes
        )
        return updatedUser.toDto()
    }

    fun deleteUser(id: UUID) {
        userAdapter.deleteUser(id)
        eventProducer.emit(User::class.java, id, EntityEventType.DELETE, mutableMapOf())
    }

    fun getUserByUsername(username: String) = userAdapter.getUserByUsername(username)

    fun getUsers(searchRequest: SearchRequest): List<User> {
        return userAdapter.getUsers(searchRequest)
    }

    fun registerGuest(): UserResponseDto {
        val guest = userAdapter.saveUser(
            User(
                username = "guest_${System.currentTimeMillis()}",
                _password = guestPassword,
                userRole = UserRole.GUEST,
                userInfo = null,
            )
        ).toDto()
        scheduleDeletion(guest.id, 15 * 60 * 1000)
        return guest
    }

    fun registerUser(dto: UserRegisterDto): UserResponseDto {
        if (!emailIsUnique(dto.email)) {
            throw IllegalArgumentException("Email already exists")
        }
        val userInfo = UserInfo(
            email = dto.email,
            firstName = dto.firstName,
            lastName = dto.lastName,
        )
        val user = User(
            username = dto.username,
            _password = "",
            userRole = UserRole.REGISTERED_USER,
            userInfo = userInfo,
        )
        user.password = dto.password
        val changes =
            EntityChangeTracker<User>().getChangedProperties(null, user).filterKeys { it != "_password" }
                .toMutableMap()

        userAdapter.saveUser(user)

        eventProducer.emit(
            User::class.java,
            user.id,
            EntityEventType.CREATE,
            changes
        )
        eventProducer.emit(
            UserInfo::class.java,
            userInfo.id,
            EntityEventType.CREATE,
            EntityChangeTracker<UserInfo>().getChangedProperties(null, userInfo)
        )
        return user.toDto()
    }

    fun importUsersFromExcel(file: MultipartFile, sheet: String, rows: Int, evaluationColumn: String) {
        // Implement the logic to import users from an Excel file
        // This is a placeholder implementation
        println("Importing users from Excel file")
    }

    fun exportUsersToExcel(filter: String?, userInfoProperties: List<String>?): ByteArray {
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
        return !userInfoAdapter.existsByMail(email)
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

    fun addPseudoPropertyToAllUsers(pseudoProperty: UserServicePseudoProperty): PseudoProperty {
        userAdapter.addPseudoPropertyToAllUsers(pseudoProperty)
        return pseudoProperty
    }

    fun renamePseudoPropertyForAllUsers(key: String, newKey: String) {
        return userAdapter.renamePseudoPropertyForAllUsers(key, newKey)
    }

}
