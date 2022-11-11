package com.example.homehelper.di

import com.example.homehelper.data.firebase.repositories_impl.*
import com.example.homehelper.domain.repositories.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface FirebaseModule {

    @Binds
    fun bindFirebaseChatsRepository(impl: FirebaseChatsRepositoryImpl): FirebaseChatsRepository

    @Binds
    fun bindFirebaseMessagesRepository(impl: FirebaseMessagesRepositoryImpl): FirebaseMessagesRepository

    @Binds
    fun bindFirebaseEventsRepository(impl: FirebaseEventsRepositoryImpl): FirebaseEventsRepository

    @Binds
    fun bindFirebaseAuthRepository(impl: FirebaseAuthRepositoryImpl): FirebaseAuthRepository

    @Binds
    fun bindFirebaseMetersDataRepository(impl: FirebaseMetersDataRepositoryImpl): FirebaseMetersDataRepository

    companion object {

        @Provides
        fun provideFirebaseAuth(): FirebaseAuth {
            return Firebase.auth
        }

        @Provides
        fun provideFirebaseDb(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }
    }
}