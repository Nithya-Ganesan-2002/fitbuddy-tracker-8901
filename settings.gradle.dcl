pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.experimental.android-ecosystem").version("0.1.43")
}

rootProject.name = "fitbuddy-tracker-8901"

// Include the nested Android build as a child project for task visibility
include("fitbuddy_mobile_app")
// The nested project declares its own submodules (app, list, utilities) via its own settings file.
