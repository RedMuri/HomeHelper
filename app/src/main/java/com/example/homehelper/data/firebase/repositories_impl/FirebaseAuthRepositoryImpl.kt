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

    override fun signIn(email: String, password: String): Task<AuthResult> {
        createUserInDb(email)
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun logIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    private fun createUserInDb(email: String) {
        val user = User(email = email)

        db.collection(FirebaseChatsRepositoryImpl.USERS)
            .document(email)
            .set(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    addMainChatToUserChats(email)
                } else
                    Log.i("muri", "createUserInDb failure: $it")
            }
    }

    private fun addMainChatToUserChats(email: String) {
        val mainChat = "main_chat"
        db.collection(FirebaseChatsRepositoryImpl.USERS).document(email).collection("chats")
            .document(mainChat)
            .set(Chat(mainChat, mainChat))
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("muri", "addMainChatToUserChats success: $it")
                } else
                    Log.i("muri", "addMainChatToUserChats failure: $it")
            }
    }
}