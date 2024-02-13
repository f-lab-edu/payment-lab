plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

dependencies {
    implementation(project(":payment-api:payment-domain"))
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    /** togglz **/
    implementation("org.togglz:togglz-spring-boot-starter:3.1.2")
    implementation("org.togglz:togglz-kotlin:2.8.0")
    implementation("org.togglz:togglz-console:3.1.2")
    implementation("org.togglz:togglz-spring-security:3.1.2")
    implementation("com.github.heneke.thymeleaf:thymeleaf-extras-togglz:1.0.1.RELEASE")

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}