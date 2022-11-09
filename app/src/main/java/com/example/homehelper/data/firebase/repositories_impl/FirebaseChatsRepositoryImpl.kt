package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.domain.repositories.FirebaseChatsRepository
import com.example.homehelper.domain.entities.Chat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class FirebaseChatsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : FirebaseChatsRepository {

    private val chats = MutableLiveData<MutableList<Chat>>()
    private val chatsList = mutableListOf<Chat>()


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
                        getChatsFromDb(chats)
                    }
                } catch (e: Exception) {
                    Log.i("muri", "getChats: exception: $e")
                }
            }
        return chats
    }

    private fun getChatsFromDb(userChats: List<Chat>) {
        for (userChat in userChats) {
            userChat.name?.let {
                db.collection(CHATS)
                    .document(it).get().addOnSuccessListener {
                        val chat = it.toObject<Chat>()
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
    }


    companion object {

        const val USERS = "users"
        const val CHATS = "chats"
        const val EVENTS_COLLECTION = "events"
        const val MAIN_MESSAGES_COLLECTION = "messages"
    }
}