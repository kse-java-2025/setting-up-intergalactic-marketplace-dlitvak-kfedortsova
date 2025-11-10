plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
	id ("org.barfuin.gradle.jacocolog") version "3.1.0"
}

group = "com.cosmocats"
version = "0.0.1-SNAPSHOT"
description = "Cosmo Cats: the Intergalactic Marketplace"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

val minimumCoveragePerFile by extra(0.8)
val filesExcludedFromCoverage by extra(listOf(
	"**/*MarketApplication.*",
	"**/config/*Configuration.*"
))

configurations {
	val compileOnly by getting {
		extendsFrom(annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

apply(from = "${rootProject.projectDir}/gradle/test.gradle")
apply(from = "${rootProject.projectDir}/gradle/jacoco.gradle")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Lombok, MapStruct, and their binding
	implementation("org.mapstruct:mapstruct:1.6.2")
	compileOnly("org.projectlombok:lombok:1.18.34")
	implementation("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	annotationProcessor("org.projectlombok:lombok:1.18.34")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

	// Data, DB, and Migration
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.liquibase:liquibase-core:4.30.0")
	implementation("org.postgresql:postgresql:42.7.4")

	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.assertj:assertj-core:3.24.2")
	testImplementation("org.wiremock:wiremock-jetty12:3.9.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// Test Annotation Processors
	testAnnotationProcessor("org.projectlombok:lombok:1.18.34")
	testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")
	testImplementation("org.projectlombok:lombok:1.18.34")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
