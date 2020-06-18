package com.teamzmron.selfstudyapp.adjective

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.teamzmron.selfstudyapp.LiveDataTestUtil
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.TestUtil
import com.teamzmron.selfstudyapp.WordDatabaseTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class AdjectiveDAOTest : WordDatabaseTest() {

    private val TEST_ENGLISH = "Convenient"
    private val TEST_JAPANESE = "Benri"
    private val TEST_TIMESTAMP = "07-2020"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    /*
    Insert, Read, delete
     */
    @Test
    internal fun insertReadDelete() {
        var adj = Adjective(TestUtil.TEST_ADJ)

        //insert
        getAdjDAO().insertAdjective(adj).blockingGet()

        //read
        val liveDataTestUtil: LiveDataTestUtil<List<Adjective>> =  LiveDataTestUtil()
        val convertedValueToLiveData: LiveData<List<Adjective>> = LiveDataReactiveStreams.fromPublisher(getAdjDAO().getAdjectives())
        var insertedAdj = liveDataTestUtil.getValue(convertedValueToLiveData)

        Assert.assertNotNull(insertedAdj)
        Assert.assertEquals(adj.englishWord, insertedAdj!![0].englishWord)
        Assert.assertEquals(adj.japaneseWord, insertedAdj[0].japaneseWord)
        Assert.assertEquals(adj.hiraganaForm, insertedAdj[0].hiraganaForm)
        Assert.assertEquals(adj.kanjiForm, insertedAdj[0].kanjiForm)
        Assert.assertEquals(adj.currentTimestamp, insertedAdj[0].currentTimestamp)
        Assert.assertEquals(adj.adjPast, insertedAdj[0].adjPast)
        Assert.assertEquals(adj.adjNegative, insertedAdj[0].adjNegative)
        Assert.assertEquals(adj.adjPastNegative, insertedAdj[0].adjPastNegative)
        Assert.assertEquals(adj.adjType, insertedAdj[0].adjType)

        adj.adjId = insertedAdj!![0].adjId
        Assert.assertEquals(adj, insertedAdj[0])

        //delete
        getAdjDAO().deleteAdjective(adj).blockingGet()

        insertedAdj = liveDataTestUtil.getValue(convertedValueToLiveData)

        Assert.assertEquals(0, insertedAdj!!.size)

    }

    @Test
    fun insertReadUpdateReadDelete() {
        var adj = Adjective(TestUtil.TEST_ADJ)

        //insert
        getAdjDAO().insertAdjective(adj).blockingGet()

        //read
        val liveDataTestUtil: LiveDataTestUtil<List<Adjective>> =  LiveDataTestUtil()
        val convertedValueToLiveData: LiveData<List<Adjective>> = LiveDataReactiveStreams.fromPublisher(getAdjDAO().getAdjectives())
        var insertedAdj = liveDataTestUtil.getValue(convertedValueToLiveData)

        Assert.assertNotNull(insertedAdj)

        Assert.assertNotNull(insertedAdj)
        Assert.assertEquals(adj.englishWord, insertedAdj!![0].englishWord)
        Assert.assertEquals(adj.japaneseWord, insertedAdj[0].japaneseWord)
        Assert.assertEquals(adj.hiraganaForm, insertedAdj[0].hiraganaForm)
        Assert.assertEquals(adj.kanjiForm, insertedAdj[0].kanjiForm)
        Assert.assertEquals(adj.currentTimestamp, insertedAdj[0].currentTimestamp)
        Assert.assertEquals(adj.adjPast, insertedAdj[0].adjPast)
        Assert.assertEquals(adj.adjNegative, insertedAdj[0].adjNegative)
        Assert.assertEquals(adj.adjPastNegative, insertedAdj[0].adjPastNegative)
        Assert.assertEquals(adj.adjType, insertedAdj[0].adjType)


        adj.adjId = insertedAdj!![0].adjId
        Assert.assertEquals(adj, insertedAdj[0])

        adj.englishWord = TEST_ENGLISH
        adj.japaneseWord = TEST_JAPANESE
        adj.currentTimestamp = TEST_TIMESTAMP

        getAdjDAO().updateAdjective(adj).blockingGet()

        insertedAdj = liveDataTestUtil.getValue(convertedValueToLiveData)

        Assert.assertEquals(adj.englishWord, insertedAdj!![0].englishWord)
        Assert.assertEquals(adj.japaneseWord, insertedAdj[0].japaneseWord)
        Assert.assertEquals(adj.hiraganaForm, insertedAdj[0].hiraganaForm)
        Assert.assertEquals(adj.kanjiForm, insertedAdj[0].kanjiForm)
        Assert.assertEquals(adj.currentTimestamp, insertedAdj[0].currentTimestamp)
        Assert.assertEquals(adj.adjPast, insertedAdj[0].adjPast)
        Assert.assertEquals(adj.adjNegative, insertedAdj[0].adjNegative)
        Assert.assertEquals(adj.adjPastNegative, insertedAdj[0].adjPastNegative)
        Assert.assertEquals(adj.adjType, insertedAdj[0].adjType)

        adj.adjId = insertedAdj!![0].adjId
        Assert.assertEquals(adj, insertedAdj[0])

        //delete
        getAdjDAO().deleteAdjective(adj).blockingGet()

        insertedAdj = liveDataTestUtil.getValue(convertedValueToLiveData)

        Assert.assertEquals(0, insertedAdj!!.size)

    }
}