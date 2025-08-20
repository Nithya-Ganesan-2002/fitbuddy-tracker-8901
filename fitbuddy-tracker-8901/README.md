# FitBuddy Workspace

This workspace contains the Android mobile app under `fitbuddy_mobile_app/`.

Build notes:
- A Gradle wrapper proxy is available in this directory:
  - ./gradlew build
- The proxy forwards to `fitbuddy_mobile_app/gradlew`.
- Alternatively, use the wrapper from the app folder directly:
  - cd fitbuddy_mobile_app
  - ./gradlew build

Run on device/emulator:
- ./gradlew :app:installDebug
- Then launch the app named "FitBuddy" on your device/emulator.

Notes:
- The project uses the Declarative Gradle DSL (.dcl files).
- Compose UI is enabled via the module configuration.
- If CI or local tooling fails with "gradlew: No such file or directory", ensure the build runs from this workspace directory or from the `fitbuddy_mobile_app` directory.
