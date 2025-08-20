val appCheckPath = ":fitbuddy_mobile_app:app:check"
val appBuildPath = ":fitbuddy_mobile_app:app:build"

tasks.register("aggregateCheck") {
    group = "verification"
    description = "Runs checks for the Android app module via workspace aggregator."
    // Only depend if the path is known in this build graph; otherwise skip to avoid configuration failure.
    doFirst {
        if (!project.gradle.startParameter.taskRequests.isEmpty()) {
            // No-op guard; dependency declared below is static
        }
    }
    dependsOn(appCheckPath)
}

tasks.register("aggregateBuild") {
    group = "build"
    description = "Builds the Android app module via workspace aggregator."
    dependsOn(appBuildPath)
}
