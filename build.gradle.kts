import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;

plugins {
    kotlin("jvm") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.github.colingrime"
version = "1.0.0-alpha"

repositories {
    mavenCentral()
    maven { setUrl("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.2.30")
    compileOnly("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.processResources {
    expand("version" to project.version)
}

tasks.withType<ShadowJar> {
    relocate("kotlin", "com.github.colingrime.shadow.kotlin")

    archiveFileName.set("${project.name}-${project.version}.jar")
    destinationDirectory.set(file("/Users/scill/Desktop/Server/plugins"))
}

tasks.register<Task>("cleanBuild") {
    dependsOn("clean")
    dependsOn("shadowJar")
    tasks.findByPath("shadowJar")?.mustRunAfter("clean")

    doLast { exec { commandLine("sh", "/Users/scill/Desktop/Scripts/reload.sh") }}
}