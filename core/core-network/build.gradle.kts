import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization")
}

kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions.jvmTarget.set(JvmTarget.JVM_11)
    }

    iosArm64()
    iosSimulatorArm64()
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
        }

        commonMain.dependencies {
            // Firebase Auth
            implementation(libs.gitlive.firebase.auth.common)
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            // Firestore
            implementation(libs.gitlive.firebase.firestore.common)
            // Serialization
            implementation(libs.kotlinx.serialization.json)

            // Core
            implementation(project(":core:core-common"))
        }

        iosMain.dependencies {

        }

        desktopMain.dependencies {
            // Coroutines
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.pe.losjardines.core.coreNetwork"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
