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

    private val names = arrayListOf("denis", "miroslava", "leonid", "grigoriy", "sofiya", "andrey", "karina", "makar", "fedor", "anna", "arina", "roman", "matvey", "zlata", "yelizaveta", "liliya", "varvara", "anastasiya", "maksim", "nikita", "yeva", "olga", "ulyana", "polina", "alona", "alina", "potr", "daniil", "vladimir", "aleksandr", "alisa", "nadezhda", "adam", "ivan", "danila", "saveliy", "bogdan", "arseniy", "mariya", "kseniya", "nina", "darya", "kirill", "melissa", "stefaniya", "zakhar", "konstantin", "demid", "kristina", "yekaterina", "yevangelina", "emiliya", "mark", "amira", "valeriya", "violetta", "vladislav", "sofya", "aleksandra", "viktoriya", "timofey", "miron", "artom", "angelina", "olesya", "yaroslav", "safiya", "artemiy", "yelena", "dmitriy", "mayya", "vera", "ilya", "tatyana", "veronika", "semon", "emil", "darina", "fatima", "aliya", "martin", "arsen", "yaroslava", "lev", "mikhail", "daniel", "leon", "agata", "marsel", "yuliya", "kira", "rodion", "gleb", "yegor", "madina", "adelina", "stepan", "melania", "timothy", "amina", "novel", "natalia", "nika", "david", "dmitry", "artyom", "savely", "victoria", "anastasia", "evgeniy", "ali", "barbarian", "artemy", "peter", "milan", "elisha", "alicia", "gregory", "alice", "sofia", "catherine", "evgenia", "mayan", "vasilisa", "fedor", "semyon", "plato", "amelia", "emin", "miroslav")

    override fun getFirebaseAuth(): FirebaseAuth = auth

    override fun signIn(email: String, password: String): Task<AuthResult> {
        for (name in names){
            val emailNew = "$name@mail.ru"
            val passwordNew = "not$name"
            val age = (18..55).random()
            val gender = listOf("m","w").random()
            val flatNumber = (1..60).random()
            createUserInDb(emailNew, gender, flatNumber, age, name)
            auth.createUserWithEmailAndPassword(emailNew, passwordNew)
        }
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun logIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    private fun createUserInDb(email: String, gender: String, flatNumber: Int, age: Int, name: String) {
        val user = User(email = email, gender = gender, flat = flatNumber, age = age, name = name)

        db.collection(FirebaseChatsRepositoryImpl.USERS).document(email).set(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    addMainChatToUserChats(email)
                } else Log.i("muri", "createUserInDb failure: $it")
            }
    }

    private fun addMainChatToUserChats(email: String) {
        val mainChat = "main_chat"
        val mainChatName = "Общий чат"
        db.collection(FirebaseChatsRepositoryImpl.USERS).document(email).collection("chats")
            .document(mainChat).set(Chat(mainChat, mainChatName)).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("muri", "addMainChatToUserChats success: $it")
                } else Log.i("muri", "addMainChatToUserChats failure: $it")
            }
    }
}