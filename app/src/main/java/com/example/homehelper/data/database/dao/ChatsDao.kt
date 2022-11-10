package com.example.homehelper.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homehelper.data.database.model.ChatDbModel
import com.example.homehelper.data.database.model.EventDbModel

@Dao
interface ChatsDao {

    @Query("select * from chats")
    fun getChats(): LiveData<List<ChatDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChat(chatDbModel: ChatDbModel)

    @Query("delete from chats where id=:chatId")
    suspend fun deleteChat(chatId: String)
}