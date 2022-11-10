package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import com.example.homehelper.domain.entities.Chat
import com.example.homehelper.domain.entities.User
import com.example.homehelper.domain.repositories.FirebaseAuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) : FirebaseAuthRepository {

    override fun getFirebaseAuth(): FirebaseAuth = auth

    override fun signIn(email: String, password: String, flatNum: Int): Task<AuthResult> {
        createUserInDb(email, flatNum)
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun logIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
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
            db.collection(FirebaseChatsRepositoryImpl.USERS).document(email).collection("chats")
                .document(chat)
                .set(Chat(chat, chat))
        }
        db.collection(FirebaseChatsRepositoryImpl.USERS)
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
}