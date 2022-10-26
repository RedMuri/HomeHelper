package com.example.homehelper.data.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.domain.Event
import com.example.homehelper.domain.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) : FirebaseRepository {

    private val events = MutableLiveData<List<Event>>()

    override fun getFirebaseAuth(): FirebaseAuth = auth

    override fun getEventsList(): LiveData<List<Event>> {
        db.collection("events")
            .orderBy("date")
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
        val event = Event(title, desc, convertMls(date))
        db.collection(EVENTS_COLLECTION)
            .add(event)
            .addOnSuccessListener { documentReference ->
                Log.i("muri", "addEvent: ${documentReference.id}")
            }.addOnFailureListener { e ->
                Log.i("muri", "addEvent error: $e")
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
    }
}