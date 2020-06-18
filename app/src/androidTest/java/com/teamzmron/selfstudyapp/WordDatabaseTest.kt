package com.teamzmron.selfstudyapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.teamzmron.selfstudyapp.Room.DAO.AdjectiveDAO
import com.teamzmron.selfstudyapp.Room.DAO.NounDAO
import com.teamzmron.selfstudyapp.Room.DAO.VerbDAO
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import org.junit.After
import org.junit.Before

abstract class WordDatabaseTest {

    private lateinit var wordDatabase: WordDatabase

    fun getNounDAO() : NounDAO = wordDatabase.nounDAO()
    fun getVerbDAO() : VerbDAO = wordDatabase.verbDao()
    fun getAdjDAO() : AdjectiveDAO = wordDatabase.adjDao()

    @Before
    fun init() {
        wordDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WordDatabase::class.java
        ).build()
    }

    @After
    fun finish() {
        wordDatabase.close()
    }


}