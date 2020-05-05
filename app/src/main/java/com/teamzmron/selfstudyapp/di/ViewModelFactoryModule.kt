package com.teamzmron.selfstudyapp.di

import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory) : ViewModelProvider.Factory

}