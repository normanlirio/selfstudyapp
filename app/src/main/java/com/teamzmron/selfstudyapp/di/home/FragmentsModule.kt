package com.teamzmron.selfstudyapp.di.home

import com.teamzmron.selfstudyapp.Fragments.AdjectiveHomeFragment
import com.teamzmron.selfstudyapp.Fragments.NounHomeFragment
import com.teamzmron.selfstudyapp.Fragments.VerbHomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeNounHomeListFragment() : NounHomeFragment

    @ContributesAndroidInjector
    abstract fun contributeVerbHomeListFragment() : VerbHomeFragment

    @ContributesAndroidInjector
    abstract fun contributeAdjHomeListFragment() : AdjectiveHomeFragment
}