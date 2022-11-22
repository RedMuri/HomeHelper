package com.example.homehelper.di

import androidx.lifecycle.ViewModel
import com.example.homehelper.presentation.viewmodels.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    @Binds
    fun bindEventsViewModel(viewModel: EventsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    @Binds
    fun bindChatViewModel(viewModel: ChatViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ChatsListViewModel::class)
    @Binds
    fun bindChatsListViewModel(viewModel: ChatsListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MetersDataViewModel::class)
    @Binds
    fun bindMetersDataViewModel(viewModel: MetersDataViewModel): ViewModel

    @IntoMap
    @ViewModelKey(UsersListViewModel::class)
    @Binds
    fun bindUsersListViewModel(viewModel: UsersListViewModel): ViewModel
}