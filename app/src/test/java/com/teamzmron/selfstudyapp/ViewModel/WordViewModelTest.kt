package com.teamzmron.selfstudyapp.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.teamzmron.selfstudyapp.Helper.TestHelper
import com.teamzmron.selfstudyapp.Room.Database.WordDatabaseTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class WordViewModelTest : WordDatabaseTest() {


    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun saveToDB() {
        val id = getWordDAO().insertWord(TestHelper.insertNewWord()).blockingGet()
        Assert.assertEquals(98, id)
        getWordById(id)
        updateWord()
        deleteWord()
    }

    fun getWordById(id: Long) {
        getWordDAO().getWordById(id.toInt())
            .test()
            .assertValue {
                it.english.equals("c")
            }
            .dispose()
    }

    fun updateWord() {
       val update = getWordDAO().updateWord(TestHelper.updateDeleteWord()).blockingGet()
        Assert.assertEquals(1, update)
    }

    fun deleteWord() {
        val del = getWordDAO().deleteWord(TestHelper.updateDeleteWord()).blockingGet()
        Assert.assertEquals(1 , del)
    }

}