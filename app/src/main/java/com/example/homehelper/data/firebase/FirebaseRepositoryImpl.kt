package com.example.homehelper.data.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.data.firebase.model.MessageDto
import com.example.homehelper.data.mappers.EventMapper
import com.example.homehelper.data.mappers.MessageMapper
import com.example.homehelper.domain.FirebaseRepository
import com.example.homehelper.domain.entities.Chat
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.entities.Message
import com.example.homehelper.domain.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val eventMapper: EventMapper,
    private val messageMapper: MessageMapper,
) : FirebaseRepository {

    private val events = MutableLiveData<List<Event>>()
    private val messages = MutableLiveData<List<Message>>()
    private val chats = MutableLiveData<MutableList<Chat>>()
    private val chatsList = mutableListOf<Chat>()

    override fun getFirebaseAuth(): FirebaseAuth = auth

    override fun signIn(email: String, password: String, flatNum: Int): Task<AuthResult> {
        createUserInDb(email, flatNum)
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun logIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
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

    private fun createUserInDb(email: String, flatNum: Int) {
        val hallwayChat = when (flatNum) {
            in 1..20 -> "hallway1"
            in 21..40 -> "hallway2"
            in 41..60 -> "hallway3"
            else -> "none"
        }
        val userChats = listOf("main_chat", hallwayChat)
        val user = User(email, flatNum, userChats)
        for (chat in userChats) {
            db.collection(USERS).document(email).collection("chats")
                .document(chat)
                .set(Chat(chat))
        }
        db.collection(USERS)
            .document(email)
            .set(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("muri", "createUserInDb success: $it")
                } else
                    Log.i("muri", "createUserInDb failure: $it")
            }.addOnFailureListener { e ->
                Log.i("muri", "createUserInDb error: $e")
            }
    }



    override fun getEventsList(): LiveData<List<Event>> {
        db.collection(EVENTS_COLLECTION)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.i("muri", "getEventsList: listen failed: $e")
                    return@addSnapshotListener
                }
                try {
                    events.value = value?.map { it.toObject() }
                } catch (e: Exception) {
                    Log.i("muri", "getEventsList: exception: $e")
                }
            }
        return events
    }

    override fun addEvent(title: String, desc: String, date: Long) {
        val id = db.collection(EVENTS_COLLECTION).document().id
        val event = Event(title, desc, eventMapper.convertMlsToDate(date), id)
        db.collection(EVENTS_COLLECTION)
            .document(id)
            .set(event)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    Log.i("muri", "addEvent success: $it")
                else
                    Log.i("muri", "addEvent failure: $it")
            }.addOnFailureListener { e ->
                Log.i("muri", "addEvent error: $e")
            }
    }

    override fun deleteEvent(eventId: String) {
        db.collection(EVENTS_COLLECTION).document(eventId).delete()
            .addOnSuccessListener {
                Log.i("muri", "deleteEvent")
            }
            .addOnFailureListener {
                Log.i("muri", "error deleteEvent")
            }
    }



    override fun getMessages(chatName: String): LiveData<List<Message>> {
        val collection = when (chatName) {
            "Main chat" -> "main_chat"
            "Hallway 1" -> "hallway1"
            "Hallway 2" -> "hallway2"
            "Hallway 3" -> "hallway3"
            else -> chatName
        }
        db.collection(CHATS).document(collection).collection("messages")
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
        val id = db.collection(CHATS).document(collection).collection("messages").document().id
        val time = System.currentTimeMillis()
        val message = MessageDto(author.trim(), text.trim(), time, id)
        db.collection(CHATS).document(collection).collection("messages")
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


    companion object {

        const val USERS = "users"
        const val CHATS = "chats"
        const val EVENTS_COLLECTION = "events"
        const val MAIN_MESSAGES_COLLECTION = "messages"
    }
}