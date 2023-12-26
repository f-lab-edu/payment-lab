dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.springframework:spring-web:6.0.7")
    implementation("org.reflections:reflections:0.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    implementation("org.togglz:togglz-spring-boot-starter:3.1.2")
    implementation("org.togglz:togglz-kotlin:2.8.0")
}