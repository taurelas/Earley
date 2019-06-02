package com.leadinsource.earley.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProjectData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
}