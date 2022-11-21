package com.example.homehelper.data.firebase.repositories_impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homehelper.data.firebase.model.UserDto
import com.example.homehelper.data.mappers.UsersMapper
import com.example.homehelper.di.UserNameQualifier
import com.example.homehelper.domain.entities.User
import com.example.homehelper.domain.repositories.FirebaseUsersRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class FirebaseUsersRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val usersMapper: UsersMapper,
    @UserNameQualifier private val userName: String,
) : FirebaseUsersRepository {

    private val users = MutableLiveData<List<User>>()

    override fun getAllUsers(): LiveData<List<User>> {
        db.collection(USERS)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    try {
                        val usersDto = task.result.documents
                            .map { it.toObject<UserDto>()!! }
                            .filter { it.email != userName }
                        users.value = usersDto.map { usersMapper.userDtoToEntity(it) }
                    } catch (e: Exception) {
                        Log.i("muri", "getAllUsers: exception: $e")
                    }
                } else {
                    Log.d("muri", "getAllUsers: failure: ", task.exception)
                }
            }
        return users
    }

    companion object {

        const val USERS = "users"
    }
}