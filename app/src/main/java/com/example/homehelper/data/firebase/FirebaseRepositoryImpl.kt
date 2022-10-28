package com.example.homehelper.data.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.domain.FirebaseRepository
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.entities.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) : FirebaseRepository {

    private val events = MutableLiveData<List<Event>>()
    private val messages = MutableLiveData<List<Message>>()

    override fun getFirebaseAuth(): FirebaseAuth = auth

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
                    messages.value = value?.map { it.toObject() }
                } catch (e: Exception) {
                    Log.i("muri", "getMessages: exception: $e")
                }
            }
        return messages
    }

    override fun sendMessage(text: String, author: String) {
        val id = db.collection(MAIN_MESSAGES_COLLECTION).document().id
        val time = System.currentTimeMillis()
        val message = Message(author.trim(), text.trim(), time,id)
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
        val event = Event(title, desc, convertMls(date), id)
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

    private fun convertMls(mls: Long?): String {
        if (mls == null) return ""
        val timestamp = Timestamp(mls)
        val date = Date(timestamp.time)
        val pattern = "dd.MM.yy  HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {

        const val EVENTS_COLLECTION = "events"
        const val MAIN_MESSAGES_COLLECTION = "messages"
    }
}