package com.teamzmron.selfstudyapp.di

import com.teamzmron.selfstudyapp.HomeActivity
import com.teamzmron.selfstudyapp.di.home.FragmentsModule
import com.teamzmron.selfstudyapp.di.home.HomeModule
import com.teamzmron.selfstudyapp.di.home.HomeViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector (
        modules = [FragmentsModule::class, HomeViewModelsModule::class, HomeModule::class]
    )
    abstract fun contributeHomeActivity() : HomeActivity
}