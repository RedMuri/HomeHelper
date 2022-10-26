package com.example.homehelper.di

import android.app.Application
import com.example.homehelper.presentation.screens.ChatFragment
import com.example.homehelper.presentation.screens.addevent.AddEventFragment
import com.example.homehelper.presentation.screens.main.EventsFragment
import com.example.homehelper.presentation.screens.auth.LogInFragment
import com.example.homehelper.presentation.screens.main.MainActivity
import com.example.homehelper.presentation.screens.auth.SignInFragment
import com.example.homehelper.presentation.screens.main.ChatsListFragment
import com.example.homehelper.presentation.screens.main.ProfileFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ViewModelModule::class, FirebaseModule::class])
interface ApplicationComponent {

    fun inject(signInFragment: SignInFragment)
    fun inject(logInFragment: LogInFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(eventsFragment: EventsFragment)
    fun inject(addEventFragment: AddEventFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(chatsListFragment: ChatsListFragment)
    fun inject(chatFragment: ChatFragment)

    @Component.Factory
    interface ApplicationComponentFactory{

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}