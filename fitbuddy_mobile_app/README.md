# FitBuddy Android App

FitBuddy helps users track daily workouts, steps, and goals with an offline-first, modern minimal UI.

## Features in this iteration
- Dashboard with bottom navigation tabs: Workouts, Steps, Goals, History
- Floating Action Button to quickly log a sample workout
- Step tracking using device sensors (Step Counter / Step Detector)
- Basic progress charts (simple bar chart) and goal progress
- Offline storage scaffold with Room (entities/dao/database/repository)
- Light theme with FitBuddy colors (primary: #2196F3, secondary: #FF9800, accent: #4CAF50)

## Build and Run
- Build: `./gradlew build`
- Install on device/emulator: `./gradlew :app:installDebug`
- Launch the app named "FitBuddy"

Note:
- Step sensor data depends on device/emulator capabilities. On devices without the sensor, the Steps screen will indicate unavailability.
- Offline sync is stubbed for now and will be implemented in future iterations.