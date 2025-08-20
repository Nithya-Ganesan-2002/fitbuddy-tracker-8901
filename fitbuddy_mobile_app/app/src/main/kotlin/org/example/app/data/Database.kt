package org.example.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [WorkoutEntity::class, StepsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FitBuddyDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun stepsDao(): StepsDao

    companion object {
        @Volatile
        private var INSTANCE: FitBuddyDatabase? = null

        fun get(context: Context): FitBuddyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FitBuddyDatabase::class.java,
                    "fitbuddy.db"
                ).fallbackToDestructiveMigration().build()
                    .also { INSTANCE = it }
            }
    }
}
