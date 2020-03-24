package com.teamzmron.selfstudyapp.Room.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.teamzmron.selfstudyapp.Room.DAO.AdjectiveDAO
import com.teamzmron.selfstudyapp.Room.DAO.VerbDAO
import com.teamzmron.selfstudyapp.Room.DAO.NounDAO
import com.teamzmron.selfstudyapp.Room.DAO.WordDAO
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.Room.Entity.Verb


@Database(entities = [Noun::class, Verb::class, Adjective::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase()  {
    abstract fun nounDAO(): NounDAO
    abstract fun verbDao() : VerbDAO
    abstract fun adjDao() : AdjectiveDAO
    abstract fun wordDAO(): WordDAO

    companion object {
        @Volatile
        private var databseInstance: WordDatabase? = null

        @JvmStatic
        fun getDatabasenIstance(mContext: Context): WordDatabase =
            databseInstance ?: synchronized(this) {
                databseInstance ?: buildDatabaseInstance(mContext).also {
                    databseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, WordDatabase::class.java, "worddb")
                .fallbackToDestructiveMigration()
                .build()

    }
}