package com.teamzmron.selfstudyapp

import android.app.Application
import android.content.Context

class SelfStudyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
       context = applicationContext

    }
    companion object {
        private var context : Context? = null
        fun getAppContext(): Context {
            return context!!
        }
    }


}