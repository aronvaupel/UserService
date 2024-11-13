package com.ecommercedemo.userservice.service.user

import com.ecommercedemo.common.kafka.EntityEventProducer
import com.ecommercedemo.common.kafka.EntityEventType
import com.ecommercedemo.common.security.EnvConfig
import com.ecommercedemo.common.util.EntityChangeTracker
import com.ecommercedemo.common.validation.userrole.UserRole
import com.ecommercedemo.userservice.dto.user.UserRegisterDto
import com.ecommercedemo.userservice.dto.user.UserResponseDto
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import com.ecommercedemo.userservice.persistence.user.IUserAdapter
import com.ecommercedemo.userservice.persistence.userinfo.IUserInfoAdapter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import org.springframework.data.redis.core.RedisTemplate

class UserServiceTest {

    private val userAdapter: IUserAdapter = mock(IUserAdapter::class.java)
    private val userInfoAdapter: IUserInfoAdapter = mock(IUserInfoAdapter::class.java)
    private val eventProducer: EntityEventProducer = mock(EntityEventProducer::class.java)

    @Suppress("UNCHECKED_CAST")
    private val redisTemplate: RedisTemplate<String, Any> =
        mock(RedisTemplate::class.java) as RedisTemplate<String, Any>
    private val userService =
        UserService(mock(EnvConfig::class.java), eventProducer, redisTemplate, userInfoAdapter, userAdapter)

    @Test
    fun `registerUser should throw exception if email is not unique`() {
        val userRegisterDto = UserRegisterDto("username", "password123", "First", "Last", "email@example.com")
        `when`(userInfoAdapter.existsByMail(userRegisterDto.email)).thenReturn(true)

        assertThrows<IllegalArgumentException> { userService.registerUser(userRegisterDto) }
        verify(userInfoAdapter).existsByMail(userRegisterDto.email)
    }

    @Test
    fun `registerUser should save user and userInfo, hash password, and emit events`() {
        val userRegisterDto = UserRegisterDto("username", "Password123!", "First", "Last", "email@example.com")
        whenever(userInfoAdapter.existsByMail(userRegisterDto.email)).thenReturn(false)

        val userCaptor = argumentCaptor<User>()
        whenever(userAdapter.saveUser(userCaptor.capture())).thenAnswer { invocation ->
            invocation.arguments[0] as User
        }

        val result: UserResponseDto = userService.registerUser(userRegisterDto)

        verify(userInfoAdapter).existsByMail(userRegisterDto.email)
        verify(userAdapter).saveUser(userCaptor.capture())

        val capturedUser = userCaptor.firstValue
        val userChanges = EntityChangeTracker<User>().getChangedProperties(null, capturedUser)
            .filterKeys { it != "_password" }
            .toMutableMap()
        assertEquals(userRegisterDto.username, capturedUser.username)
        assertEquals(UserRole.REGISTERED_USER, capturedUser.userRole)
        assertEquals(userRegisterDto.email, capturedUser.userInfo?.email)
        assertEquals(userRegisterDto.firstName, capturedUser.userInfo?.firstName)
        assertEquals(userRegisterDto.lastName, capturedUser.userInfo?.lastName)
        verify(eventProducer).emit(
            User::class.java,
            capturedUser.id,
            EntityEventType.CREATE,
            userChanges
        )
        verify(eventProducer).emit(
            UserInfo::class.java,
            capturedUser.userInfo!!.id,
            EntityEventType.CREATE,
            EntityChangeTracker<UserInfo>().getChangedProperties(null, capturedUser.userInfo!!)
        )
        assertEquals(capturedUser.toDto(), result)
    }
}
