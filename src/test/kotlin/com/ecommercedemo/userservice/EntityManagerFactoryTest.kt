package com.ecommercedemo.userservice

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import jakarta.persistence.EntityManagerFactory
import kotlin.test.assertNotNull

@SpringBootTest
class EntityManagerFactoryTest {

    @Autowired
    private lateinit var entityManagerFactory: EntityManagerFactory

    @Test
    fun testEntityManagerFactory() {
        assertNotNull(entityManagerFactory, "EntityManagerFactory should be present")
    }
}