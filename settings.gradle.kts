rootProject.name = "LosJardinesKMP"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")
// core
include(":core:core-common")
include(":core:core-database")
include(":core:core-network")
include(":core:core-ui")
// domain
include(":domain")
// feature
include(":feature:ui")
include(":feature:data")
