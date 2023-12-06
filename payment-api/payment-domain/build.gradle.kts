dependencies {
    implementation(project(":common"))
    implementation("org.hibernate:hibernate-core:6.1.7.Final")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}