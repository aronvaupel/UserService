package com.ecommercedemo.userservice.controller

import com.ecommercedemo.userservice.service.redis.RedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisController @Autowired constructor(private val redisService: RedisService) {

    @GetMapping("/save")
    fun save(@RequestParam key: String, @RequestParam value: String): String {
        redisService.save(key, value)
        return "Saved"
    }

    @GetMapping("/get")
    fun get(@RequestParam key: String): String? {
        return redisService.get(key)
    }
}