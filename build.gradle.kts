import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

sourceSets.main {
    java.srcDirs("src")
    resources.srcDirs("src").exclude("**/*.kt")
}
sourceSets.test {
    java.srcDirs("test")
    resources.srcDirs("test").exclude("**/*.kt")
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

