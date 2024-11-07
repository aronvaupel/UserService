plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "com.ecommercedemo"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

fun loadEnv(): Map<String, String> {
	val envFile = file("${rootProject.projectDir}/.env")
	if (!envFile.exists()) {
		throw GradleException(".env file not found")
	}

	return envFile.readLines()
		.filter { it.isNotBlank() && !it.startsWith("#") }
		.map { it.split("=", limit = 2) }
		.associate { it[0] to it.getOrElse(1) { "" } }
}

extra["springCloudVersion"] = "2023.0.3"

repositories {
	mavenCentral()
	maven {
		url = uri("https://maven.pkg.github.com/aronvaupel/Commons")
		credentials {
			val env = loadEnv()
			username = env["GITHUB_USERNAME"] ?: ""
			password = env["GITHUB_TOKEN"] ?: ""
		}
	}
	maven {
		url = uri("https://repo.spring.io/milestone")
	}
	maven {
		url = uri("https://repo.spring.io/snapshot")
	}

}

val isDevProfile: Boolean = project.hasProperty("spring.profiles.active") && project.property("spring.profiles.active") == "dev"
val isLocalProfile: Boolean = project.hasProperty("spring.profiles.active") && project.property("spring.profiles.active") == "local"

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.module:jackson-module-jakarta-xmlbind-annotations:2.15.2")
	implementation("com.github.aronvaupel:commons:1.3.37")
	implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
	implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.8.3")
	implementation("org.hibernate:hibernate-core:6.6.1.Final")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("org.glassfish.jaxb:jaxb-runtime:2.3.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.postgresql:postgresql:42.7.2")
	implementation("org.springframework.boot:spring-boot-docker-compose")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	//Fixme
	implementation("org.springframework.boot:spring-boot-starter-security:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	if (!isDevProfile && !isLocalProfile) {
		implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	}
	implementation("org.springframework.kafka:spring-kafka")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	implementation("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	if (!isLocalProfile) {
		testImplementation("org.springframework.security:spring-security-test")
	}

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
