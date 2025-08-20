tasks {
    register("check") {
        dependsOn = [":fitbuddy_mobile_app:app:check"]
        group = "verification"
        description = "Runs checks for the Android app module from the workspace."
    }
    register("build") {
        dependsOn = [":fitbuddy_mobile_app:app:build"]
        group = "build"
        description = "Builds the Android app module from the workspace."
    }
}
