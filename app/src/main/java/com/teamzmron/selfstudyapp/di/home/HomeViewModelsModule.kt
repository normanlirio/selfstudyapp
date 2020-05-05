package com.teamzmron.selfstudyapp.di.home

import com.teamzmron.selfstudyapp.ViewModel.AdjectiveViewModel
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import com.teamzmron.selfstudyapp.ViewModel.VerbViewModel
import com.teamzmron.selfstudyapp.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(NounViewModel::class)
    abstract fun bindNounViewModel(viewModel : NounViewModel) : NounViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VerbViewModel::class)
    abstract fun bindNounViewModel(viewModel : VerbViewModel) : VerbViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AdjectiveViewModel::class)
    abstract fun bindNounViewModel(viewModel : AdjectiveViewModel) : AdjectiveViewModel
}