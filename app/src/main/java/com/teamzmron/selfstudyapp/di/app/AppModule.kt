package com.teamzmron.selfstudyapp.di.app

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(application: Application) : WordDatabase {
        return WordDatabase.getDatabasenIstance(application)
    }



}