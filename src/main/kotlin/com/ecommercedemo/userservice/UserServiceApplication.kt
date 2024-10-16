package com.ecommercedemo.userservice

import com.ecommercedemo.common.redis.RedisConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(RedisConfig::class)
class UserServiceApplication

fun main(args: Array<String>) {
	runApplication<UserServiceApplication>(*args)
}
