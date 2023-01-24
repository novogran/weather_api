import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
    kotlin("plugin.serialization").version("1.8.0")
    kotlin("kapt") version "1.7.21"
}

val ktor_version: String by project
val dagger_version = "2.44.2"

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    kapt("com.google.dagger:dagger-compiler:$dagger_version")
    implementation("com.google.dagger:dagger:$dagger_version")
}
repositories {
    mavenCentral()
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
