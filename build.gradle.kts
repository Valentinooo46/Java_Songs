plugins {
	java
	id("org.springframework.boot") version "4.0.6"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "ua.valentino"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	//Jakarta validation
	implementation("jakarta.validation:jakarta.validation-api:3.1.0")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
	//MP3
	implementation("com.mpatric:mp3agic:0.9.1")
	// MapStruct API
    implementation("org.mapstruct:mapstruct:1.6.3")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    // Annotation processor для генерації коду
    //Lombok-mapstruct binding
	annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	compileOnly ("org.projectlombok:lombok-mapstruct-binding:0.2.0")

	//Thumbnailator 0.4.21
	implementation("net.coobird:thumbnailator:0.4.21")
	//datafaker
	implementation("net.datafaker:datafaker:2.1.0")

	//Thymeleaf
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:4.0.1")
	 // Spring Boot Starter для JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	// JPA API (анотації jakarta.persistence.*)
    // implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // Hibernate Core (реалізація JPA)
    // implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
	 implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	// Драйвер для PostgreSQL (якщо ти використовуєш Postgres)
    implementation("org.postgresql:postgresql:42.7.3")
	//Lombok
	compileOnly("org.projectlombok:lombok:1.18.44")
    annotationProcessor("org.projectlombok:lombok:1.18.44")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
