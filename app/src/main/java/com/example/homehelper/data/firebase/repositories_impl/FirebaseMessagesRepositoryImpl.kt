package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.data.firebase.FirebaseRepositoryImpl
import com.example.homehelper.data.firebase.model.MessageDto
import com.example.homehelper.data.mappers.EventMapper
import com.example.homehelper.data.mappers.MessageMapper
import com.example.homehelper.domain.entities.Message
import com.example.homehelper.domain.usecases.repositories.FirebaseMessagesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class FirebaseMessagesRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val messageMapper: MessageMapper,
): FirebaseMessagesRepository {
    // TODO add mappers for dto->entity transforming

    private val messages = MutableLiveData<List<Message>>()


    override fun getMessages(chatName: String): LiveData<List<Message>> {
        val collection = when (chatName) {
            "Main chat" -> "main_chat"
            "Hallway 1" -> "hallway1"
            "Hallway 2" -> "hallway2"
            "Hallway 3" -> "hallway3"
            else -> chatName
        }
        db.collection(FirebaseRepositoryImpl.CHATS).document(collection).collection("messages")
            .orderBy("time")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.i("muri", "getMessages: listen failed: $e")
                    return@addSnapshotListener
                }
                try {
                    val messagesDto = value?.map { it.toObject<MessageDto>() }
                    messages.value = messagesDto?.map { messageMapper.messageDtoToEntity(it) }
                } catch (e: Exception) {
                    Log.i("muri", "getMessages: exception: $e")
                }
            }
        return messages
    }

    override fun sendMessage(text: String, author: String, chatName: String) {
        val collection = when (chatName) {
            "Main chat" -> "main_chat"
            "Hallway 1" -> "hallway1"
            "Hallway 2" -> "hallway2"
            "Hallway 3" -> "hallway3"
            else -> chatName
        }
        val id = db.collection(FirebaseRepositoryImpl.CHATS).document(collection).collection("messages").document().id
        val time = System.currentTimeMillis()
        val message = MessageDto(author.trim(), text.trim(), time, id)
        db.collection(FirebaseRepositoryImpl.CHATS).document(collection).collection("messages")
            .document(id)
            .set(message)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    Log.i("muri", "sendMessage success: $it")
                else
                    Log.i("muri", "sendMessage failure: $it")
            }.addOnFailureListener { e ->
                Log.i("muri", "sendMessage error: $e")
            }
    }
}