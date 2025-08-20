tasks.register("check") {
    group = "verification"
    description = "Runs checks for the Android app module from the workspace."
    dependsOn(":fitbuddy_mobile_app:app:check")
}

tasks.register("build") {
    group = "build"
    description = "Builds the Android app module from the workspace."
    dependsOn(":fitbuddy_mobile_app:app:build")
}
