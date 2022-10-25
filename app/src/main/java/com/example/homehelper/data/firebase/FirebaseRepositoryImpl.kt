package com.example.homehelper.data.firebase

import android.util.Log
import com.example.homehelper.domain.Event
import com.example.homehelper.domain.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): FirebaseRepository{

    override fun getFirebaseAuth(): FirebaseAuth = auth

    override fun getEventsList(): List<Event> {
        return listOf()
    }

    override fun addEvent(event: Event) {
        db.collection(EVENTS_COLLECTION)
            .add(event)
            .addOnSuccessListener { documentReference ->
                Log.i("muri","snap id: ${documentReference.id}")
            }.addOnFailureListener { e->
                Log.i("muri","snap with error: $e")
            }
    }

    companion object{

        const val EVENTS_COLLECTION = "events"
    }
}