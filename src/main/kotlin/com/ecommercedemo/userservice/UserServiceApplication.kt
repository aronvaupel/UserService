package com.ecommercedemo.userservice

import com.ecommercedemo.common.application.kafka.EntityEventProducer
import com.ecommercedemo.common.application.validation.country.Country
import com.ecommercedemo.common.application.validation.modification.ModificationType
import com.ecommercedemo.common.application.validation.password.PasswordCrypto
import com.ecommercedemo.common.application.validation.userrole.UserRole
import com.ecommercedemo.common.service.concretion.EntityChangeTracker
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import com.ecommercedemo.userservice.persistence.user.UserPersistenceAdapter
import com.github.javafaker.Faker
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@SpringBootApplication(scanBasePackages = ["com.ecommercedemo.common", "com.ecommercedemo.userservice"])
@EntityScan("com.ecommercedemo.userservice.model", "com.ecommercedemo.common.model")
@EnableJpaRepositories("com.ecommercedemo.common.persistence", "com.ecommercedemo.userservice.persistence")
@EnableScheduling
class UserServiceApplication

//Todo: make java faker optional
fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}

@Component
class UserDataUploader(
    private val userPersistenceAdapter: UserPersistenceAdapter,
    private val entityChangeTracker: EntityChangeTracker<User>,
    private val eventProducer: EntityEventProducer,
) : CommandLineRunner {

    val log = KotlinLogging.logger {}

    override fun run(vararg args: String?) {
        val createUsers = System.getenv("CREATE_USERS")?.toBoolean() ?: false

        if (!createUsers) {
            log.info ("User creation skipped.")
            return
        } else log.info("Start seeding user data...")

        val faker = Faker()
        val users = mutableListOf<User>()

        val allowedRoles = UserRole.entries.filter { it != UserRole.SUPER_ADMIN && it != UserRole.ADMIN }

        repeat(100) {
            val userInfo = UserInfo(
                firstName = faker.name().firstName().take(50),
                lastName = faker.name().lastName().take(50),
                email = generateUniqueEmail(faker),
                phoneNumber = faker.phoneNumber().cellPhone(),
                street = faker.address().streetName(),
                houseNumber = faker.address().buildingNumber().take(99),
                additionalAddressInfo = faker.address().secondaryAddress(),
                zipCode = faker.address().zipCode(),
                city = faker.address().city(),
                country = Country.entries.toTypedArray().random()
            )

            val rawPassword = generateValidPassword()
            val hashedPassword = PasswordCrypto.hashPassword(rawPassword)

            val user = User(
                username = faker.name().username(),
                _password = hashedPassword,
                userRole = allowedRoles.random(),
                userInfo = userInfo,
                lastActive = LocalDateTime.now().minusDays(faker.number().numberBetween(0, 365).toLong())
            )
            log.info("Generated user: $user")
            users.add(user)
            val changes = entityChangeTracker.getChangedProperties(null, user)
            eventProducer. emit(
                User::class.simpleName!!,
                user.id,
                ModificationType.CREATE,
                changes
            )
        }

        log.info("Saving user data...")
        userPersistenceAdapter.saveAll(users)
        log.info("User data uploaded.")

    }

    fun generateValidPassword(): String {
        val faker = Faker()
        val specialCharacters = listOf('@', '#', '$', '%', '^', '&', '+', '=', '!')

        return buildString {
            append(faker.regexify("[A-Z]{1}"))
            append(faker.regexify("[a-z]{1}"))
            append(faker.regexify("[0-9]{1}"))
            append(specialCharacters.random())
            append(faker.regexify("[A-Za-z0-9@#\$%^&+=!]{4,95}"))
        }.also {

            require(it.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+$).{8,100}$"))) {
                "Generated password does not meet the constraints."
            }
        }
    }

    val emailSet = mutableSetOf<String>()

    fun generateUniqueEmail(faker: Faker): String {
        var email: String
        do {
            email = faker.internet().emailAddress()
        } while (!emailSet.add(email))
        return email
    }

}


