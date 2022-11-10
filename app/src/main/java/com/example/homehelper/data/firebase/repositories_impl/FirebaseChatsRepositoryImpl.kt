package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.data.database.dao.ChatsDao
import com.example.homehelper.data.firebase.model.ChatDto
import com.example.homehelper.data.mappers.ChatMapper
import com.example.homehelper.domain.entities.Chat
import com.example.homehelper.domain.repositories.FirebaseChatsRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FirebaseChatsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val chatMapper: ChatMapper,
    private val chatsDao: ChatsDao,
) : FirebaseChatsRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

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
            db.collection(CHATS).document(userChat).get().addOnSuccessListener {
                try {
                    insertChatToDb(it)
                } catch (e: Exception) {
                    Log.i("muri", "loadUserChatsFromFb: exception: $e")
                }
            }.addOnFailureListener {
                Log.i("muri", "getChats error: $it")
            }
        }
    }

    private fun insertChatToDb(it: DocumentSnapshot) {
        val chatDto = it.toObject<ChatDto>()
        if (chatDto != null) {
            val chatDbModel = chatMapper.mapChatDtoToChatDbModel(chatDto)
            scope.launch {
                chatsDao.addChat(chatDbModel)
            }
        }
    }


    override fun startChatWithSomeone(userEmail: String, someoneEmail: String) {
//        val chatAcceptors = listOf(userEmail, someoneEmail)
//        createChat(someoneEmail)
//        for (chatAcceptor in chatAcceptors) {
//            db.collection(USERS).document(chatAcceptor).collection("chats")
//                .document(someoneEmail)
//                .set(Chat(someoneEmail)).addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        Log.i("muri", "startChatWithSomeone success: $it")
//                    } else Log.i("muri", "startChatWithSomeone failure: $it")
//                }.addOnFailureListener { e ->
//                    Log.i("muri", "startChatWithSomeone error: $e")
//                }
//        }
    }

    private fun createChat(chatName: String) {
//        val chat = Chat(chatName)
//        db.collection(CHATS).document(chatName).set(chat)
    }

    override fun getChats(userEmail: String): LiveData<List<Chat>> {
        val chatsDbModel = chatsDao.getChats()
        return chatMapper.mapChatsDbModelToChats(chatsDbModel)
    }


    companion object {

        const val USERS = "users"
        const val CHATS = "chats"
    }
}