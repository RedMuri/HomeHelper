package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import androidx.lifecycle.LiveData
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

    override fun loadUserChatsFromFb(userEmail: String) {
        db.collection(USERS).document(userEmail).collection("chats")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.i("muri", "getChats: listen failed: $e")
                    return@addSnapshotListener
                }
                try {
                    //TODO add UserChatId data class in firebase model
                    scope.launch {
                        chatsDao.deleteAllChats()
                    }
                    val userChats = value?.map { it.toObject<ChatDto>() }
                    if (userChats != null) {
                        loadChatsById(userChats)
                    }
                } catch (e: Exception) {
                    Log.i("muri", "getChats: exception: $e")
                }
            }
    }

    private fun loadChatsById(userChats: List<ChatDto>) {

        for (userChat in userChats) {
            insertChatToDb(userChat)
//            db.collection(CHATS)
//                .document(userChat)
//                .get().addOnSuccessListener {
//                    try {
//
//                    } catch (e: Exception) {
//                        Log.i("muri", "loadUserChatsFromFb: exception: $e")
//                    }
//                }.addOnFailureListener {
//                    Log.i("muri", "getChats error: $it")
//                }
//        }
        }
    }

    private fun insertChatToDb(chatDto: ChatDto) {
//        val chatDto = it.toObject<ChatDto>()
        val chatDbModel = chatMapper.mapChatDtoToChatDbModel(chatDto)
        scope.launch {
            chatsDao.addChat(chatDbModel)
        }
    }


    override fun startChatWithSomeone(
        userEmail: String,
        someoneEmail: String,
        callback: (String) -> Unit,
    ) {
        if (userEmail == someoneEmail)
            return
        val chatAcceptors = listOf(userEmail, someoneEmail)
        //val id = db.collection(CHATS).document().id
        val chatDto =
            ChatDto(chatAcceptors.sorted().joinToString("_"),
                chatAcceptors.joinToString("||"))

        db.collection(CHATS).document(chatDto.id).set(chatDto).addOnCompleteListener {
            if (it.isSuccessful) {
                callback.invoke(chatDto.id)
                addChatToChatAcceptors(chatAcceptors, chatDto)
                Log.i("muri", "startChatWithSomeone success: $it")
            } else {
                Log.i("muri", "startChatWithSomeone failure: $it")
            }
        }
    }

    private fun addChatToChatAcceptors(
        chatAcceptors: List<String>,
        chatDto: ChatDto,
    ) {
        val chatNames = chatDto.name.split("||")
        for (chatAcceptor in chatAcceptors) {
            val chatNameForCurrentUser = chatNames.filterNot { it == chatAcceptor }.joinToString()
            val chatForUser = ChatDto(chatDto.id, chatNameForCurrentUser)
            db.collection(USERS).document(chatAcceptor).collection(CHATS).document(chatDto.id)
                .set(chatForUser)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.i("muri", "addChatToChatAcceptors success: $it")
                    } else {
                        Log.i("muri", "addChatToChatAcceptors failure: $it")
                    }
                }
        }
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