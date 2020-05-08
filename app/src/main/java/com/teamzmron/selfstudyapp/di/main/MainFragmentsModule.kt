package com.teamzmron.selfstudyapp.di.main

import com.teamzmron.selfstudyapp.ui.Fragments.Adjective.AdjectiveHomeFragment
import com.teamzmron.selfstudyapp.ui.Fragments.Home
import com.teamzmron.selfstudyapp.ui.Fragments.noun.NounHomeFragment
import com.teamzmron.selfstudyapp.ui.Fragments.verb.VerbHomeFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeNounHomeListFragment() : NounHomeFragment


    @ContributesAndroidInjector
    abstract fun contributeVerbHomeListFragment() : VerbHomeFragment


    @ContributesAndroidInjector
    abstract fun contributeAdjHomeListFragment() : AdjectiveHomeFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment() : Home
}