package com.teamzmron.selfstudyapp.Room.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.teamzmron.selfstudyapp.Room.DAO.WordDAO
import com.teamzmron.selfstudyapp.Room.Entity.Word


@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase()  {
    abstract fun wordDao(): WordDAO

    companion object {
        @Volatile
        private var databseInstance: WordDatabase? = null

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