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

}

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.github.aronvaupel:commons:1.0.0")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.postgresql:postgresql")
	implementation("org.springframework.boot:spring-boot-docker-compose")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.kafka:spring-kafka")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	implementation("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
