import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
}

kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
    }

    iosArm64()
    iosSimulatorArm64()
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            // Koin
            implementation(libs.koin.android)
            // Preview
            implementation(libs.androidx.compose.ui.tooling.preview)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            // Date time
            implementation(libs.kotlinx.datetime)
            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            // Core
            implementation(project(":core:core-ui"))
            implementation(project(":core:core-common"))
            implementation(project(":domain"))
        }

        iosMain.dependencies {

        }

        desktopMain.dependencies {
            // Coroutines
            implementation(libs.kotlinx.coroutines.swing)
            // Galeria de previews de escritorio (ver preview/gallery/PreviewGallery.kt)
            implementation(compose.desktop.currentOs)
        }
    }
}

/**
 * Lanza la galeria de previews de escritorio (feature/ui/src/desktopMain/.../preview/gallery).
 * Uso: ./gradlew :feature:ui:runPreviewGallery
 */
tasks.register<JavaExec>("runPreviewGallery") {
    group = "preview"
    description = "Abre la galeria de previews de Compose Desktop"

    val desktopMainCompilation = kotlin.jvm("desktop").compilations.getByName("main")
    classpath(desktopMainCompilation.output.allOutputs, desktopMainCompilation.runtimeDependencyFiles)
    mainClass.set("com.pe.losjardines.preview.gallery.PreviewGalleryKt")
}

android {
    namespace = "com.pe.losjardines.feature.ui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
