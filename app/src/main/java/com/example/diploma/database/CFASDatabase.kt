package com.example.diploma.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.diploma.models.Worker

@Database(entities = [Worker::class], version = 1, exportSchema = false)
abstract class CFASDatabase : RoomDatabase() {
    abstract fun cfasDao(): CFASDao
    companion object {
        @Volatile
        private var INSTANCE: CFASDatabase? = null

        fun getDatabase(context: Context): CFASDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CFASDatabase::class.java,
                    "cfas_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}