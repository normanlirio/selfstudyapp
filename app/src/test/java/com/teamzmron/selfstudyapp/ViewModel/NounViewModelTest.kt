package com.teamzmron.selfstudyapp.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.teamzmron.selfstudyapp.Helper.TestHelper
import com.teamzmron.selfstudyapp.Room.Database.NounDatabaseTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class NounViewModelTest : NounDatabaseTest() {


    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun saveToDB() {
        val id = getWordDAO().insertNoun(TestHelper.insertNewWord()).blockingGet()
        Assert.assertEquals(98, id)
        getWordById(id)
        updateWord()
        deleteWord()
    }

    fun getWordById(id: Long) {
        getWordDAO().getNounById(id.toInt())
            .test()
            .assertValue {
                it.english.equals("c")
            }
            .dispose()
    }

    fun updateWord() {
       val update = getWordDAO().updateNoun(TestHelper.updateDeleteWord()).blockingGet()
        Assert.assertEquals(1, update)
    }

    fun deleteWord() {
        val del = getWordDAO().deleteNoun(TestHelper.updateDeleteWord()).blockingGet()
        Assert.assertEquals(1 , del)
    }

}