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
        compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.all {
            linkerOpts("-framework", "SystemConfiguration")
        }
    }
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            // Apache
            implementation(libs.apache.poi.ooxml)
        }
        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            // Date Time
            implementation(libs.kotlinx.datetime)
        }

        iosMain.dependencies {

        }

        desktopMain.dependencies {
            // Apache
            implementation(libs.apache.poi.ooxml)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
