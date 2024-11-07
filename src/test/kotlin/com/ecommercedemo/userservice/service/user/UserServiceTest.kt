package com.ecommercedemo.userservice.service.user

import com.ecommercedemo.common.kafka.EntityEventProducer
import com.ecommercedemo.common.kafka.EntityEventType
import com.ecommercedemo.common.security.EnvConfig
import com.ecommercedemo.common.validation.userrole.UserRole
import com.ecommercedemo.userservice.dto.user.RegisterUserDto
import com.ecommercedemo.userservice.dto.user.UserDto
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
    private val redisTemplate: RedisTemplate<String, Any> = mock(RedisTemplate::class.java) as RedisTemplate<String, Any>
    private val userService = UserService(userInfoAdapter, mock(EnvConfig::class.java), eventProducer, redisTemplate, userAdapter)

    @Test
    fun `registerUser should throw exception if email is not unique`() {
        val registerUserDto = RegisterUserDto("username", "password123", "First", "Last", "email@example.com")
        `when`(userInfoAdapter.existsByMail(registerUserDto.email)).thenReturn(true)

        assertThrows<IllegalArgumentException> { userService.registerUser(registerUserDto) }
        verify(userInfoAdapter).existsByMail(registerUserDto.email)
    }

    @Test
    fun `registerUser should save user and userInfo, hash password, and emit events`() {
        val registerUserDto = RegisterUserDto("username", "Password123!", "First", "Last", "email@example.com")
        whenever(userInfoAdapter.existsByMail(registerUserDto.email)).thenReturn(false)

        val userCaptor = argumentCaptor<User>()
        whenever(userAdapter.saveUser(userCaptor.capture())).thenAnswer { invocation ->
            invocation.arguments[0] as User
        }

        val result: UserDto = userService.registerUser(registerUserDto)

        verify(userInfoAdapter).existsByMail(registerUserDto.email)
        verify(userAdapter).saveUser(userCaptor.capture())

        val capturedUser = userCaptor.firstValue
        assertEquals(registerUserDto.username, capturedUser.username)
        assertEquals(UserRole.REGISTERED_USER, capturedUser.userRole)
        assertEquals(registerUserDto.email, capturedUser.userInfo?.email)
        assertEquals(registerUserDto.firstName, capturedUser.userInfo?.firstName)
        assertEquals(registerUserDto.lastName, capturedUser.userInfo?.lastName)
        verify(eventProducer).emit(
            User::class.java,
            capturedUser.id,
            EntityEventType.CREATE,
            capturedUser.getChangedProperties())
        verify(eventProducer).emit(
            UserInfo::class.java,
            capturedUser.userInfo!!.id,
            EntityEventType.CREATE,
            capturedUser.userInfo!!.getChangedProperties())
        assertEquals(capturedUser.toDto(), result)
    }
}
