package com.example.homehelper.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homehelper.data.database.dao.EventsDao
import com.example.homehelper.data.database.model.EventDbModel

@Database(entities = [EventDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "home_helper.db"
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let { return it }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val db =
                    Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME)
                        .build()
                INSTANCE = db
                return db
            }
        }

        const val EVENTS_TABLE_NAME = "events"
    }

    abstract fun eventsDao(): EventsDao
}