androidApplication {
    namespace = "org.example.app"

    compose {
        enabled = true
    }

    dependencies {
        // Jetpack Compose BOM
        implementation(platform("androidx.compose:compose-bom:2024.06.00"))
        implementation("androidx.activity:activity-compose:1.9.0")
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3:1.2.1")

        // Core & lifecycle
        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")

        // Sensors and annotations
        implementation("androidx.annotation:annotation:1.8.0")

        // Room (runtime + ktx). Compiler is omitted here due to unsupported annotationProcessor/ksp in declarative DSL context.
        implementation("androidx.room:room-runtime:2.6.1")
        implementation("androidx.room:room-ktx:2.6.1")
    }
}
