package com.example.homehelper.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homehelper.data.database.model.EventDbModel

@Dao
interface EventsDao {

    @Query("select * from events")
    fun getEvents(): LiveData<List<EventDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvents(listOfEventDbModel: List<EventDbModel>)

    @Query("delete from events where id=:eventId")
    suspend fun deleteEvent(eventId: String)
}