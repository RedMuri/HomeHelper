package com.example.homehelper.data.firebase

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.data.firebase.model.MessageDto
import com.example.homehelper.data.mappers.EventMapper
import com.example.homehelper.data.mappers.MessageMapper
import com.example.homehelper.domain.FirebaseRepository
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.entities.Message
import com.example.homehelper.domain.entities.User
import com.example.homehelper.presentation.HomeHelperApp
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val eventMapper: EventMapper,
    private val messageMapper: MessageMapper,
    private val application: Application,
) : FirebaseRepository {

    private val events = MutableLiveData<List<Event>>()
    private val messages = MutableLiveData<List<Message>>()

    override fun getFirebaseAuth(): FirebaseAuth = auth

    override fun signIn(
        email: String,
        password: String,
        flatNum: Int,
    ): Task<AuthResult> {
        createUserInDb(email, flatNum)
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun logIn(email: String, password: String): Task<AuthResult> {
        getUserFromDb(email)
        return auth.signInWithEmailAndPassword(email, password)
    }

    private fun getUserFromDb(email: String) {
        db.collection(USERS)
            .document(email).get().addOnSuccessListener {
                val user = it.toObject<User>()
                if (user?.flatNum!=null && user.email!=null) {
                    (application as HomeHelperApp).sharedPreferences.edit()
                        .putInt(HomeHelperApp.USER_FLAT_NUM, user.flatNum)
                        .putString(HomeHelperApp.USER_EMAIL, user.email)
                        .apply()
                }
                Log.i("muri", "getUserFromDb success: $it")
            }.addOnFailureListener {
                Log.i("muri", "getUserFromDb error: $it")
            }
    }

    private fun createUserInDb(email: String, flatNum: Int) {
        val user = User(email, flatNum)
        db.collection(USERS)
            .document(email)
            .set(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    (application as HomeHelperApp).sharedPreferences.edit()
                        .putInt(HomeHelperApp.USER_FLAT_NUM, flatNum)
                        .putString(HomeHelperApp.USER_EMAIL, email)
                        .apply()
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

    override fun getMessages(): LiveData<List<Message>> {
        db.collection(MAIN_MESSAGES_COLLECTION)
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

    override fun sendMessage(text: String, author: String) {
        val id = db.collection(MAIN_MESSAGES_COLLECTION).document().id
        val time = System.currentTimeMillis()
        val message = MessageDto(author.trim(), text.trim(), time, id)
        db.collection(MAIN_MESSAGES_COLLECTION)
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


    companion object {

        const val USERS = "users"
        const val EVENTS_COLLECTION = "events"
        const val MAIN_MESSAGES_COLLECTION = "messages"
    }
}