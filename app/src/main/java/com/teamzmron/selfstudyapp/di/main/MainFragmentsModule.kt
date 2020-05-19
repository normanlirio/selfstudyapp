package com.teamzmron.selfstudyapp.di.main

import com.teamzmron.selfstudyapp.ui.Fragments.Adjective.AdjectiveAddFragment
import com.teamzmron.selfstudyapp.ui.Fragments.Adjective.AdjectiveHomeFragment
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.Fragments.Home
import com.teamzmron.selfstudyapp.ui.Fragments.noun.NounAddFragment
import com.teamzmron.selfstudyapp.ui.Fragments.noun.NounEditFragment
import com.teamzmron.selfstudyapp.ui.Fragments.noun.NounHomeFragment
import com.teamzmron.selfstudyapp.ui.Fragments.verb.VerbAddFragment
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

    @ContributesAndroidInjector
    abstract fun contributeNounAddFragment() : NounAddFragment

    @ContributesAndroidInjector
    abstract fun contributeVerbAddFragment() : VerbAddFragment

    @ContributesAndroidInjector
    abstract fun contributeAdjectiveAddFragment() : AdjectiveAddFragment

    @ContributesAndroidInjector
    abstract fun contributeBaseFragment() : BaseFragment

    @ContributesAndroidInjector
    abstract fun contributeNounEditFragment() : NounEditFragment
}