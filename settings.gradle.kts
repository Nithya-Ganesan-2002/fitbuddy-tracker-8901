rootProject.name = "fitbuddy-tracker-8901"

// Include the nested Android build root as a project and map to its directory.
// Its own settings.gradle.dcl declares app, list, utilities modules.
include(":fitbuddy_mobile_app")
project(":fitbuddy_mobile_app").projectDir = file("fitbuddy_mobile_app")
