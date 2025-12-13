plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization")

    id("app.cash.sqldelight") version libs.versions.sqldelight
}

kotlin {

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    iosArm64()
    iosSimulatorArm64()
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            // AndroidDx
            implementation(libs.androidx.startup.runtime)
            // SQLDelight
            implementation(libs.sqldelight.android.driver)
        }

        commonMain.dependencies {
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            // SQLDelight
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.sqldelight.stately.common)
        }

        iosMain.dependencies {
            // SQLDelight
            implementation(libs.sqldelight.ios.driver)
        }

        desktopMain.dependencies {
            // SQLDelight
            implementation(libs.sqldelight.desktop.driver)
        }
    }
}

android {
    namespace = "com.pe.losjardines.core.coreDatabase"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

sqldelight{
    databases{
        create("Database"){
            packageName.set("com.pe.losjardines.cache")
        }
    }
}
