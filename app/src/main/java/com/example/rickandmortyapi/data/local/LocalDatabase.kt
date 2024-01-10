package com.example.rickandmortyapi.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmortyapi.data.model.LocalModel


@Database(entities = [LocalModel::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract val dao: LocalDao
}

private lateinit var INSTANCE: LocalDatabase

fun getDatabase(context: Context): LocalDatabase {

    synchronized(LocalDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                LocalDatabase::class.java,
                "database"
            ).build()
        }
        return INSTANCE
    }
}