plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.plugin.compose")
    // Kapt used for Room compiler if we decide to enable it later
    // id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

android {
    namespace = "org.example.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.gradle.experimental.android.app"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "0.1"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Reduce variant count to speed up CI: disable AndroidTest variants and unitTests by default
    testOptions {
        unitTests.isIncludeAndroidResources = false
        animationsDisabled = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Jetpack Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    // Material icons (extended) for BarChart, DirectionsRun, History, etc.
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    // Add Material Components for theme parent resolution
    implementation("com.google.android.material:material:1.12.0")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Core & lifecycle
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")

    // Annotations
    implementation("androidx.annotation:annotation:1.8.0")

    // Room (runtime + ktx). Compiler not enabled yet to avoid KSP/Kapt setup here.
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    // kapt("androidx.room:room-compiler:2.6.1") // enable if Kapt plugin is added
}
