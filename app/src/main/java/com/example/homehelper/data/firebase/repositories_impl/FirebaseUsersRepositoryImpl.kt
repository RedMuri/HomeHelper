package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.data.firebase.model.MessageDto
import com.example.homehelper.domain.entities.User
import com.example.homehelper.domain.repositories.FirebaseUsersRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class FirebaseUsersRepositoryImpl@Inject constructor(
    private val db: FirebaseFirestore,
) : FirebaseUsersRepository {

    private val users = MutableLiveData<List<User>>()

    override fun getAllUsers():  {
        db.collection(USERS)
            .orderBy("email")
            .get()
            .addOnSuccessListener{ snapshot ->
                try {
                    val usersDto = snapshot?.map { it.toObject<User>() }
                    users.value = usersDto?.map { messageMapper.messageDtoToEntity(it) }
                } catch (e: Exception) {
                    Log.i("muri", "getMessages: exception: $e")
                }
            }
            .addOnFailureListener {
                Log.i("muri", "getAllUsers: exception: $it")
            }
        return users
    }

    companion object {

        const val USERS = "users"
    }
}