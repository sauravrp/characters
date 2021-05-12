package com.takehome.sauravrp.di.components.modules

import android.content.Context
import androidx.room.Room
import com.takehome.sauravrp.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun appDb(context: Context) : AppDatabase {
        return Room.databaseBuilder(context,
            AppDatabase::class.java, "character-db").build()
    }
}