package com.example.homehelper.di

import android.app.Application
import com.example.homehelper.presentation.screens.EventsFragment
import com.example.homehelper.presentation.screens.LogInFragment
import com.example.homehelper.presentation.screens.MainActivity
import com.example.homehelper.presentation.screens.SignInFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ViewModelModule::class, FirebaseModule::class])
interface ApplicationComponent {

    fun inject(signInFragment: SignInFragment)
    fun inject(logInFragment: LogInFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(eventsFragment: EventsFragment)

    @Component.Factory
    interface ApplicationComponentFactory{

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}