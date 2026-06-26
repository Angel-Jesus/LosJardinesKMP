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
                jvmTarget = "17"
            }
        }
    }

    iosArm64()
    iosSimulatorArm64()
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            // SQLDelight
            implementation(libs.sqldelight.android.driver)
            // Koin
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            // SQLDelight
            implementation(libs.sqldelight.coroutines.extensions)
            // Koin
            implementation(libs.koin.core)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight{
    databases{
        create("Database"){
            packageName.set("com.pe.losjardines.cache")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            verifyMigrations.set(true)
            version = 1
        }
    }
}
