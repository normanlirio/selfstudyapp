package com.teamzmron.selfstudyapp.di.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.teamzmron.selfstudyapp.Adapters.AdjectiveAdapter
import com.teamzmron.selfstudyapp.Adapters.NounAdapter
import com.teamzmron.selfstudyapp.Adapters.VerbAdapter
import com.teamzmron.selfstudyapp.Repository.AdjectiveRepository
import com.teamzmron.selfstudyapp.Repository.NounRepository
import com.teamzmron.selfstudyapp.Repository.VerbRepository
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.ui.activities.HomeActivity
import dagger.Module
import dagger.Provides


@Module
class MainModule {

    @MainScope
    @Provides
    fun provideNounAdapter() : NounAdapter = NounAdapter()

    @MainScope
    @Provides
    fun provideAdjectiveAdapter() : AdjectiveAdapter = AdjectiveAdapter()

    @MainScope
    @Provides
    fun provideVerbAdapter() : VerbAdapter = VerbAdapter()

    @MainScope
    @Provides
    fun verbRepository(wordDatabase: WordDatabase) : VerbRepository = VerbRepository(wordDatabase)

    @MainScope
    @Provides
    fun nounRepository(wordDatabase: WordDatabase) : NounRepository = NounRepository(wordDatabase)

    @MainScope
    @Provides
    fun adjRepository(wordDatabase: WordDatabase) : AdjectiveRepository = AdjectiveRepository(wordDatabase)

}