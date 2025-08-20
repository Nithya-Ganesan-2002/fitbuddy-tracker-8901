package org.example.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WorkoutEntity)

    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun observeAll(): Flow<List<WorkoutEntity>>
}

@Dao
interface StepsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: StepsEntity)

    @Query("SELECT * FROM daily_steps WHERE date = :date LIMIT 1")
    fun observeForDate(date: String): Flow<StepsEntity?>
}
