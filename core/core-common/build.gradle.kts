import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
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
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            // Firebase Auth
            implementation(libs.gitlive.firebase.auth.common)
            // Firestore
            implementation(libs.gitlive.firebase.firestore.common)
            // Date Time
            implementation(libs.kotlinx.datetime)
        }

        iosMain.dependencies {

        }

        desktopMain.dependencies {

        }
    }
}

android {
    namespace = "com.pe.losjardines.core.coreCommon"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
