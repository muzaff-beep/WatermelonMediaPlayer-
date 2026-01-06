plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.1" }
}

dependencies {
    // --- CORE UI & ENGINE ---
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")

    // --- MEDIA3 (THE HEART OF WATERMELON) ---
    // Optimized for 4K playback and subtitle rendering
    implementation("androidx.media3:media3-exoplayer:1.2.0")
    implementation("androidx.media3:media3-ui:1.2.0")
    implementation("androidx.media3:media3-session:1.2.0")

    // --- DATA & STORAGE ---
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.navigation:navigation-compose:2.7.5")
}
