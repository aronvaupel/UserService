package com.ecommercedemo.userservice.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaStartupConfirmationConfig {
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String? = null

    @PostConstruct
    fun logKafkaBootstrapServers() {
        println("Kafka Bootstrap Servers: $bootstrapServers")
    }
}