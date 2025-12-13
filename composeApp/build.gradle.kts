import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.nativeCocoapods)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.composeReload)
    alias(libs.plugins.google.services)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    cocoapods {
        summary = "Módulo compartido para iOS"
        homepage = "https://tuprojecto.dev"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            // Koin
            implementation(libs.koin.android)
            // Ktor
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.voyager.koin)
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            // Library Navigation
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tabNavigator)
            // DatePicker KMP
            implementation(libs.kmp.date.time.picker)
            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            // Firebase Auth
            implementation(libs.gitlive.firebase.auth.common)
            //Domain
            implementation(project(":domain"))
            // Feature Login UI
            implementation(project(":feature:ui"))
            // Data
            implementation(project(":feature:data"))
            // Core
            implementation(project(":core:core-ui"))
            implementation(project(":core:core-network"))
            implementation(project(":core:core-database"))
        }

        iosMain.dependencies {
            // Ktor
            implementation(libs.ktor.client.darwin)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            // Coroutines
            implementation(libs.kotlinx.coroutines.swing)
            // Koin
            implementation(libs.koin.core)
            // Ktor
            implementation(libs.ktor.client.cio)
        }
    }
}

android {
    namespace = "com.pe.losjardines"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.pe.losjardines"
        minSdk = 35//libs.versions.android.minSdk.get().toInt()
        targetSdk = 35//libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.desktop {
    application {
        mainClass = "com.pe.losjardines.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.pe.losjardines"
            packageVersion = "1.0.0"
        }
    }
}

buildkonfig {
    packageName = "com.pe.losjardines"

    val localProperties = Properties().apply {
        val propsFile = rootProject.file("local.properties")
        if(propsFile.exists()) load(propsFile.inputStream())
    }

    defaultConfigs {
        // Firebase Config
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "PROJECT_ID",
            localProperties["PROJECT_ID"]?.toString() ?: ""
        )

        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "APPLICATION_ID",
            localProperties["APPLICATION_ID"]?.toString() ?: ""
        )

        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "API_KEY",
            localProperties["API_KEY"]?.toString() ?: ""
        )

    }
}
