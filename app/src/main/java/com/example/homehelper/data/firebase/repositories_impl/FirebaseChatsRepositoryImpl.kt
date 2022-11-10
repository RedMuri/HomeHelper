package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.data.firebase.model.ChatDto
import com.example.homehelper.data.firebase.model.EventDto
import com.example.homehelper.domain.repositories.FirebaseChatsRepository
import com.example.homehelper.domain.entities.Chat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class FirebaseChatsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
) : FirebaseChatsRepository {

    private val chats = MutableLiveData<MutableList<Chat>>()
    private val chatsList = mutableListOf<Chat>()


    suspend fun loadChatsFromFb(userEmail: String) {
        db.collection(USERS).document(userEmail).collection("chats")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.i("muri", "getChats: listen failed: $e")
                    return@addSnapshotListener
                }
                try {
                    val userChats = value?.map { it.toObject<String>() }
                    if (userChats != null) {
                        loadUserChatsFromFb(userChats)
                    }
                } catch (e: Exception) {
                    Log.i("muri", "getChats: exception: $e")
                }
            }
    }

    private fun loadUserChatsFromFb(userChats: List<String>) {
        for (userChat in userChats) {
            db.collection(CHATS)
                .document(userChat).get().addOnSuccessListener {
                    try {
                        val chatDto = it.toObject<ChatDto>()

                    }
                    if (chat != null) {
                        chatsList.add(chat)
                        chats.value = chatsList
                    }
                    //                Log.i("muri", "getChats success: $it")
                }.addOnFailureListener {
                    Log.i("muri", "getChats error: $it")
                }
        }
    }


    private suspend fun insertChatToDb(value: QuerySnapshot?) {
        val eventsDto = value?.map { it.toObject<EventDto>() }
        val eventsDbModel =
            eventsDto?.map { eventMapper.mapEventDtoToEventDbModel(it) }
        eventsDbModel?.let {
            eventsDao.addEvents(it)
        }
    }


    override fun startChatWithSomeone(userEmail: String, someoneEmail: String) {
        val chatAcceptors = listOf(userEmail, someoneEmail)
        createChat(someoneEmail)
        for (chatAcceptor in chatAcceptors) {
            db.collection(USERS)
                .document(chatAcceptor)
                .collection("chats")
                .document(someoneEmail)
                .set(Chat(someoneEmail))
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.i("muri", "startChatWithSomeone success: $it")
                    } else
                        Log.i("muri", "startChatWithSomeone failure: $it")
                }.addOnFailureListener { e ->
                    Log.i("muri", "startChatWithSomeone error: $e")
                }
        }
    }

    private fun createChat(chatName: String) {
        val chat = Chat(chatName)
        db.collection(CHATS).document(chatName).set(chat)
    }

    override fun getChats(userEmail: String): MutableLiveData<MutableList<Chat>> {
        db.collection(USERS).document(userEmail).collection("chats")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.i("muri", "getChats: listen failed: $e")
                    return@addSnapshotListener
                }
                try {
                    val chats = value?.map { it.toObject<Chat>() }
                    if (chats != null) {
                        loadUserChatsFromFb(chats)
                    }
                } catch (e: Exception) {
                    Log.i("muri", "getChats: exception: $e")
                }
            }
        return chats
    }


    companion object {

        const val USERS = "users"
        const val CHATS = "chats"
    }
}