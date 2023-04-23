import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.5" apply false
	id("io.spring.dependency-management") version "1.1.0" apply false
	id("org.asciidoctor.jvm.convert") version "3.3.2" apply false

	kotlin("jvm") version "1.7.22" apply false
	kotlin("plugin.spring") version "1.7.22" apply false
	kotlin("plugin.jpa") version "1.7.22" apply false
	kotlin("kapt") version "1.7.22" apply false
}

allprojects {
	repositories {
		mavenCentral()
	}
}

subprojects {
	group = project.properties["projectGroups"].toString()
	version = project.properties["applicationVersion"].toString()

	apply {
		plugin("org.asciidoctor.jvm.convert")
		plugin("org.jetbrains.kotlin.jvm")
		plugin("java")
		plugin("java-library")

		plugin("kotlin")
		plugin("kotlin-kapt")
		plugin("kotlin-spring")
		plugin("kotlin-jpa")
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	tasks.named<Jar>("jar") {
		enabled = true
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}