import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.allopen") version "1.8.20"
    kotlin("plugin.jpa") version "1.8.20"
    id("io.quarkus")
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("$quarkusPlatformGroupId:$quarkusPlatformArtifactId:$quarkusPlatformVersion"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("io.quarkus:quarkus-jdbc-h2")
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("at.favre.lib:bcrypt:0.10.2")
    implementation("io.quarkus:quarkus-resteasy-mutiny")
    testImplementation("io.quarkus:quarkus-test-security-jwt")
    testImplementation("io.rest-assured:rest-assured")
}
//
///** Kotlin linter settings ************************/
//ktlint {
//    verbose.set(true)
//}

group = "az.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging { showStandardStreams = true }
    }

    wrapper {
        distributionType = ALL
    }

    withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = VERSION_17.toString()
    }
}
