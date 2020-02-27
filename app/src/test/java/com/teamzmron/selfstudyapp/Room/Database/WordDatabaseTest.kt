package com.teamzmron.selfstudyapp.Room.Database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.teamzmron.selfstudyapp.Room.DAO.WordDAO
import com.teamzmron.selfstudyapp.ViewModel.WordViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
abstract class WordDatabaseTest {

    private  var wordDatabase: WordDatabase ? = null

    @InjectMocks
    lateinit var wordViewModel: WordViewModel

    @Before
    fun setUp() {
        wordDatabase = Room.inMemoryDatabaseBuilder<WordDatabase>(ApplicationProvider.getApplicationContext(), WordDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        wordDatabase!!.close()
    }

    fun getWordDAO() : WordDAO {
       return wordDatabase!!.wordDao()
    }

    @Test
    fun getDatabasenIstance() {
    }
}