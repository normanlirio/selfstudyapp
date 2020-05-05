package com.teamzmron.selfstudyapp.di.home

import com.teamzmron.selfstudyapp.Adapters.AdjectiveAdapter
import com.teamzmron.selfstudyapp.Adapters.NounAdapter
import com.teamzmron.selfstudyapp.Adapters.VerbAdapter
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @HomeScope
    @Provides
    fun provideNounAdapter() : NounAdapter = NounAdapter()

    @HomeScope
    @Provides
    fun provideAdjectiveAdapter() : AdjectiveAdapter = AdjectiveAdapter()

    @HomeScope
    @Provides
    fun provideVerbAdapter() : VerbAdapter = VerbAdapter()

}