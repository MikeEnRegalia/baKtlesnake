plugins {
    // You can update this to the newest Kotlin version, if you want
    kotlin("jvm") version "1.7.0"
    // This plugin is used for (de)serialization
    kotlin("plugin.serialization") version "1.7.0"
    // This is a built-in plugin used for running, building and testing!
    application
}

group = "com.example" // Put your name here!
version = "1.0"

// Declares the main class. Change this value if you rename Battlesnake.kt!
val mainName = "com.example.battlesnake.BattlesnakeKt"

// Declares usage of the Maven Central repository
// Do not remove!
repositories {
    mavenCentral()
}

dependencies {
    // kotlinx.serialization dependencies
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    // Ktor dependencies
    serverImplementation("core", "netty", "content-negotiation", "default-headers")
    ktorImplementation("serialization-kotlinx-json")

    // Logging library used by Ktor
    // Remove this if you don't want logging
    implementation("ch.qos.logback:logback-classic:1.2.11")
}

// Utility functions for declaring dependencies
// This can be simplified by typing out the full Ktor module names,
// but because of the modular structure of Ktor, this will probably work a little bit better for most people
fun DependencyHandlerScope.serverImplementation(vararg names: String) =
    names.forEach { ktorImplementation("server-$it") }

fun DependencyHandlerScope.ktorImplementation(name: String) = implementation(
    group = "io.ktor",
    name = "ktor-$name",
    version = "2.0.1"
)

// Declares the creation of a "fat jar"
tasks {
    withType<Jar>().configureEach {
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest { attributes("Main-Class" to mainName) }
    }
}

// Allows you to run using gradlew
// Specifies the main class we specified earlier
application {
    mainClass.set(mainName)
}