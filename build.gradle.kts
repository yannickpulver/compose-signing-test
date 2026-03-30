plugins {
    kotlin("jvm") version "2.2.0"
    id("org.jetbrains.compose") version "9999.0.0-SNAPSHOT"
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.0"
}

import org.jetbrains.compose.desktop.application.dsl.TargetFormat

repositories {
    mavenLocal()
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            packageName = "compose-signing-test"
            targetFormats(TargetFormat.Pkg)
            macOS {
                bundleID = "com.example.composesigningtest"
            }
        }
    }
}
