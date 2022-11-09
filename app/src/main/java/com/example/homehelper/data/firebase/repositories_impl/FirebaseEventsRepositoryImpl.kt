package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.data.firebase.FirebaseRepositoryImpl
import com.example.homehelper.data.mappers.EventMapper
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.usecases.repositories.FirebaseEventsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class FirebaseEventsRepositoryImpl@Inject constructor(
    private val db: FirebaseFirestore,
    private val eventMapper: EventMapper,
): FirebaseEventsRepository {

    private val events = MutableLiveData<List<Event>>()

    override fun getEventsList(): LiveData<List<Event>> {
        db.collection(FirebaseRepositoryImpl.EVENTS_COLLECTION)
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
        val id = db.collection(FirebaseRepositoryImpl.EVENTS_COLLECTION).document().id
        val event = Event(title, desc, eventMapper.convertMlsToDate(date), id)
        db.collection(FirebaseRepositoryImpl.EVENTS_COLLECTION)
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
        db.collection(FirebaseRepositoryImpl.EVENTS_COLLECTION).document(eventId).delete()
            .addOnSuccessListener {
                Log.i("muri", "deleteEvent")
            }
            .addOnFailureListener {
                Log.i("muri", "error deleteEvent")
            }
    }

}