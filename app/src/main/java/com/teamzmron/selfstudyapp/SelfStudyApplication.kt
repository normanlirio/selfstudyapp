package com.teamzmron.selfstudyapp

import android.app.Application
import android.content.Context
import com.teamzmron.selfstudyapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SelfStudyApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
       context = applicationContext

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }


    companion object {
        private var context : Context? = null
        fun getAppContext(): Context {
            return context!!
        }
    }


}