package com.teamzmron.selfstudyapp.di.app

import com.teamzmron.selfstudyapp.ui.activities.HomeActivity
import com.teamzmron.selfstudyapp.di.main.MainFragmentsModule
import com.teamzmron.selfstudyapp.di.main.MainModule
import com.teamzmron.selfstudyapp.di.main.MainScope
import com.teamzmron.selfstudyapp.di.main.MainViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector (
        modules = [MainFragmentsModule::class, MainViewModelsModule::class, MainModule::class]
    )
    abstract fun contributeHomeActivity() : HomeActivity
}