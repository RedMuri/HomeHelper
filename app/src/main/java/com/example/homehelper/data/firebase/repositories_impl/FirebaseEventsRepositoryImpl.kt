package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.data.database.dao.EventsDao
import com.example.homehelper.data.firebase.model.EventDto
import com.example.homehelper.data.mappers.EventMapper
import com.example.homehelper.domain.entities.Event
import com.example.homehelper.domain.repositories.FirebaseEventsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FirebaseEventsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val eventMapper: EventMapper,
    private val eventsDao: EventsDao,
) : FirebaseEventsRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getEvents(): LiveData<List<Event>> {
        val eventsDbModel = eventsDao.getEvents()
        return eventMapper.mapEventsDbModelToEvents(eventsDbModel)
    }

    override fun loadEventsFromFb() {
        db.collection(EVENTS_COLLECTION)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.i("muri", "getEventsList: listen failed: $e")
                    return@addSnapshotListener
                }
                try {
                    insertDataToDb(value)
                } catch (e: Exception) {
                    Log.i("muri", "getEventsList: exception: $e")
                }
            }
    }

    private fun insertDataToDb(value: QuerySnapshot?) {
        val eventsDto = value?.map { it.toObject<EventDto>() }
        val eventsDbModel =
            eventsDto?.map { eventMapper.mapEventDtoToEventDbModel(it) }
        eventsDbModel?.let {
            scope.launch {
                eventsDao.addEvents(it)
            }
        }
    }


    override fun addEvent(title: String, desc: String, date: Long) {
        val id = db.collection(EVENTS_COLLECTION).document().id
        val eventDto = EventDto(title, desc, eventMapper.convertMlsToDate(date), id)
        db.collection(EVENTS_COLLECTION)
            .document(id)
            .set(eventDto)
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

        const val EVENTS_COLLECTION = "events"
    }
}