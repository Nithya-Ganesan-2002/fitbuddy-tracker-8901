package org.example.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val durationMinutes: Int,
    val calories: Int,
    val date: String // ISO string for simplicity
)

@Entity(tableName = "daily_steps")
data class StepsEntity(
    @PrimaryKey val date: String, // yyyy-MM-dd
    val steps: Int
)
