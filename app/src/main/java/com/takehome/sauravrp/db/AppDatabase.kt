package com.takehome.sauravrp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.takehome.sauravrp.viewmodels.ShowCharacter
import javax.inject.Singleton

@Singleton
@Database(entities = arrayOf(ShowCharacter::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}