package com.ecommercedemo.userservice.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClientAutoConfiguration
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("dev")
@EnableAutoConfiguration(exclude = [
    EurekaClientAutoConfiguration::class,
    EurekaDiscoveryClientConfiguration::class,
    CompositeDiscoveryClientAutoConfiguration::class
])
class ServiceDiscoveryConfig