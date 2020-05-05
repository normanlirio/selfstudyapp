package com.teamzmron.selfstudyapp.di

import android.app.Application
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideRoomDatabase(application: Application) : WordDatabase {
        return WordDatabase.getDatabasenIstance(application)
    }

}