package com.example.homehelper.di

import com.example.homehelper.data.firebase.FirebaseRepositoryImpl
import com.example.homehelper.domain.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface FirebaseModule {

    @Binds
    fun bindRepository(impl: FirebaseRepositoryImpl): FirebaseRepository

    companion object{

        @Provides
        fun provideFirebaseAuth(): FirebaseAuth {
            return Firebase.auth
        }
    }
}