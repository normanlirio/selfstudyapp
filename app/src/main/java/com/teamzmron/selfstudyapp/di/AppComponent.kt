package com.teamzmron.selfstudyapp.di

import android.app.Application
import com.teamzmron.selfstudyapp.SelfStudyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component (
    modules = [AndroidSupportInjectionModule::class, ActivityBuildersModule::class, AppModule::class]
)
interface AppComponent : AndroidInjector<SelfStudyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application) : Builder

        fun build() : AppComponent
    }

}