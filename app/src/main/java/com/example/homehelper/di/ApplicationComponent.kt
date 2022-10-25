package com.example.homehelper.di

import android.app.Application
import com.example.homehelper.presentation.screens.AddEventFragment
import com.example.homehelper.presentation.screens.main.EventsFragment
import com.example.homehelper.presentation.screens.auth.LogInFragment
import com.example.homehelper.presentation.screens.main.MainActivity
import com.example.homehelper.presentation.screens.auth.SignInFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ViewModelModule::class, FirebaseModule::class])
interface ApplicationComponent {

    fun inject(signInFragment: SignInFragment)
    fun inject(logInFragment: LogInFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(eventsFragment: EventsFragment)
    fun inject(addEventFragment: AddEventFragment)

    @Component.Factory
    interface ApplicationComponentFactory{

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}