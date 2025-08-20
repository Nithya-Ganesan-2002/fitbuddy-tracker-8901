package org.example.app.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDate

class FitRepository(context: Context) {
    private val db = FitBuddyDatabase.get(context)
    private val workouts = db.workoutDao()
    private val steps = db.stepsDao()

    suspend fun addWorkout(title: String, duration: Int, calories: Int, date: LocalDate) {
        withContext(Dispatchers.IO) {
            workouts.insert(
                WorkoutEntity(
                    title = title,
                    durationMinutes = duration,
                    calories = calories,
                    date = date.toString()
                )
            )
        }
    }

    fun observeWorkouts(): Flow<List<WorkoutEntity>> = workouts.observeAll()

    suspend fun upsertSteps(date: LocalDate, count: Int) {
        withContext(Dispatchers.IO) {
            steps.upsert(StepsEntity(date = date.toString(), steps = count))
        }
    }

    fun observeSteps(date: LocalDate): Flow<StepsEntity?> = steps.observeForDate(date.toString())
}

// Placeholder for future network sync
class SyncManager(private val context: Context, private val repository: FitRepository) {
    // PUBLIC_INTERFACE
    suspend fun syncNow(): Boolean {
        // In a future iteration, push/pull data with a backend.
        // For now, simply return success.
        return true
    }
}
