package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.data.firebase.model.MessageDto
import com.example.homehelper.data.firebase.model.UserDto
import com.example.homehelper.data.mappers.UsersMapper
import com.example.homehelper.domain.entities.User
import com.example.homehelper.domain.repositories.FirebaseUsersRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class FirebaseUsersRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val usersMapper: UsersMapper,
) : FirebaseUsersRepository {

    private val users = MutableLiveData<List<User>>()

    override fun getAllUsers(): LiveData<List<User>> {
        db.collection(USERS)
            .orderBy("email")
            .get()
            .addOnSuccessListener { snapshot ->
                try {
                    val usersDto = snapshot?.map { it.toObject<UserDto>() }
                    users.value = usersDto?.map { usersMapper.userDtoToEntity(it) }
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