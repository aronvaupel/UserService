package com.ecommercedemo.userservice.service.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService @Autowired constructor(private val redisTemplate: RedisTemplate<String, String>) {

    fun save(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun get(key: String): String? {
        return redisTemplate.opsForValue().get(key)
    }
}