package com.ecommercedemo.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(scanBasePackages = ["com.ecommercedemo.common", "com.ecommercedemo.userservice"])
@EntityScan("com.ecommercedemo.userservice.model", "com.ecommercedemo.common.model")
@EnableJpaRepositories("com.ecommercedemo.common.persistence", "com.ecommercedemo.userservice.persistence")
@EnableScheduling
class UserServiceApplication

fun main(args: Array<String>) {
	runApplication<UserServiceApplication>(*args)
}
