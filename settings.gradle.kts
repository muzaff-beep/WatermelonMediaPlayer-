// [ENGINEERING BLOCK: SETTINGS CONFIGURATION]
// This file defines where Gradle should look for plugins and libraries.

pluginManagement {
    repositories {
        google()            // CRITICAL: Look here for the Android Application Plugin
        mavenCentral()      // Look here for Kotlin and other standard plugins
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()            // CRITICAL: Look here for Media3 and UI libraries
        mavenCentral()      // Look here for everything else
    }
}

rootProject.name = "WatermelonPlayer"
include(":app")

