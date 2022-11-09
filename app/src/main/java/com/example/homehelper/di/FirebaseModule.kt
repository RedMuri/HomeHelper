package com.example.homehelper.di

import com.example.homehelper.data.firebase.repositories_impl.FirebaseChatsRepositoryImpl
import com.example.homehelper.data.firebase.repositories_impl.FirebaseAuthRepositoryImpl
import com.example.homehelper.data.firebase.repositories_impl.FirebaseEventsRepositoryImpl
import com.example.homehelper.data.firebase.repositories_impl.FirebaseMessagesRepositoryImpl
import com.example.homehelper.domain.repositories.FirebaseChatsRepository
import com.example.homehelper.domain.repositories.FirebaseAuthRepository
import com.example.homehelper.domain.repositories.FirebaseEventsRepository
import com.example.homehelper.domain.repositories.FirebaseMessagesRepository
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

    companion object{

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