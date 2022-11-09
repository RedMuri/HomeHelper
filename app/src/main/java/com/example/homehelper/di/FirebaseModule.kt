package com.example.homehelper.di

import com.example.homehelper.data.firebase.FirebaseRepositoryImpl
import com.example.homehelper.data.firebase.repositories_impl.FirebaseEventsRepositoryImpl
import com.example.homehelper.data.firebase.repositories_impl.FirebaseMessagesRepositoryImpl
import com.example.homehelper.domain.FirebaseRepository
import com.example.homehelper.domain.usecases.repositories.FirebaseEventsRepository
import com.example.homehelper.domain.usecases.repositories.FirebaseMessagesRepository
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
    fun bindRepository(impl: FirebaseRepositoryImpl): FirebaseRepository

    @Binds
    fun bindFirebaseMessagesRepository(impl: FirebaseMessagesRepositoryImpl): FirebaseMessagesRepository

    @Binds
    fun bindFirebaseEventsRepository(impl: FirebaseEventsRepositoryImpl): FirebaseEventsRepository

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