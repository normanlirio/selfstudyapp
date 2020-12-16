package com.teamzmron.selfstudyapp.Room.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.teamzmron.selfstudyapp.Helper.TestHelper
import com.teamzmron.selfstudyapp.Room.DAO.AdjectiveDAO
import com.teamzmron.selfstudyapp.Room.DAO.NounDAO
import com.teamzmron.selfstudyapp.Room.DAO.VerbDAO
import com.teamzmron.selfstudyapp.Room.DAO.WordDAO
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import java.util.concurrent.Executors


@Database(entities = [Noun::class, Verb::class, Adjective::class], version = 1)
abstract class WordDatabase : RoomDatabase()  {
    abstract fun nounDAO(): NounDAO
    abstract fun verbDao() : VerbDAO
    abstract fun adjDao() : AdjectiveDAO
    abstract fun wordDAO(): WordDAO

    companion object {
        @Volatile
        private var databaseInstance: WordDatabase? = null

        @JvmStatic
        fun getDatabaseInstance(mContext: Context): WordDatabase =
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: buildDatabaseInstance(mContext).also {
                    databaseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext.applicationContext, WordDatabase::class.java, "worddb")
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadExecutor().execute {
                                TestHelper.insertInitialNoun().forEach {
                                    getDatabaseInstance(mContext).nounDAO().insertInitialNoun(it)
                                }

                            }

                            Executors.newSingleThreadExecutor().execute {
                                TestHelper.insertInitialVerb().forEach {
                                    getDatabaseInstance(mContext).verbDao().insertInitialVerb(it)
                                }

                            }

                            Executors.newSingleThreadExecutor().execute {
                                TestHelper.insertInitiaAdjective().forEach {
                                    getDatabaseInstance(mContext).adjDao().insertInitialAdjective(it)
                                }

                            }

                        }
                    }
                )
                .fallbackToDestructiveMigration()
                .build()

    }
}