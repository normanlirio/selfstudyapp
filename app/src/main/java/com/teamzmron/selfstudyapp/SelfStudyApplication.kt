package com.teamzmron.selfstudyapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase

class SelfStudyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
       context = applicationContext

    }
    companion object {
        private var context : Context? = null
       @JvmStatic fun getAppContext(): Context {
            return context!!
        }
    }


}