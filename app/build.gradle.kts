plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Added for Room compiler (KSP is recommended over annotationProcessor)
    id("com.google.devtools.ksp") version "1.9.24-1.0.21" // Match your Kotlin version
}

android {
    namespace = "com.watermelon.player"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.watermelon.player"
        minSdk = 26 // Support for Android 8.0+
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0-core"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release {
            // ENHANCEMENT: Minification removes unused code to keep the APK light.
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // FIX: Required for Media3 and AI-Sync (Whisper) internal logic
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.17" // Updated to latest stable for Kotlin 1.9+
    }
}

dependencies {
    // Core AndroidX + Kotlin
    implementation("androidx.core:core-ktx:1.13.1") // Updated
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6") // Updated

    // Jetpack Compose BOM + Core (fixes Modifier, LocalContext, composable, etc.)
    val composeBom = platform("androidx.compose:compose-bom:2024.10.00") // Latest stable BOM
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Material3 Theme & Components
    implementation("androidx.compose.material3:material3")

    // Activity Compose → Provides LocalContext
    implementation("androidx.activity:activity-compose:1.9.2") // Updated

    // Navigation Compose → Fixes navigation, composable, NavHost, MainNavGraph
    implementation("androidx.navigation:navigation-compose:2.8.3") // Updated

    // Room Database → Fixes VideoEntity, database, Room entities/DAO
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion") // KSP for faster compilation

    // Media3 / ExoPlayer → Fixes loadcontrol, 4K playback, subtitles
    val media3Version = "1.4.1" // Updated to latest stable
    implementation("androidx.media3:media3-exoplayer:$media3Version")
    implementation("androidx.media3:media3-ui:$media3Version")
    implementation("androidx.media3:media3-session:$media3Version")
    implementation("androidx.media3:media3-common:$media3Version")

    // Optional: Google Fonts support (fixes font() in Compose)
    implementation("androidx.compose.ui:ui-text-google-fonts")

    // Material Components (your original)
    implementation("com.google.android.material:material:1.12.0") // Updated
}
