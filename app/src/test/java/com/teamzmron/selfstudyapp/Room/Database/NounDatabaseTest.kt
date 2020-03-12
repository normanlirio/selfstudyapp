package com.teamzmron.selfstudyapp.Room.Database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.teamzmron.selfstudyapp.Room.DAO.NounDAO
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
abstract class NounDatabaseTest {

    private  var wordDatabase: WordDatabase ? = null

    @InjectMocks
    lateinit var nounViewModel: NounViewModel

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

    fun getWordDAO() : NounDAO {
       return wordDatabase!!.nounDAO()
    }

    @Test
    fun getDatabasenIstance() {
    }
}