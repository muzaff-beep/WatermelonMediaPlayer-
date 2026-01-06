pluginManagement {
    repositories {
        google() // REQUIRED: Tells Gradle where the Android plugins live.
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google() // REQUIRED: Tells Gradle where Media3 and Google libraries live.
        mavenCentral()
    }
}

// Defines the project name and connects the 'app' folder logic.
rootProject.name = "WatermelonMediaPlayer"
include(":app")

