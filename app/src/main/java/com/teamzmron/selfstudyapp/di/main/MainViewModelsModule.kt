package com.teamzmron.selfstudyapp.di.main

import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.ViewModel.*
import com.teamzmron.selfstudyapp.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(NounViewModel::class)
    abstract fun bindNounViewModel(viewModel : NounViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VerbViewModel::class)
    abstract fun bindVerbViewModel(viewModel : VerbViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AdjectiveViewModel::class)
    abstract fun bindAdjectiveViewModel(viewModel : AdjectiveViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WordViewModel::class)
    abstract fun bindWordViewModel(viewModel : WordViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SharedViewModel::class)
    abstract fun bindSharedViewModel(viewModel : SharedViewModel) : ViewModel
}