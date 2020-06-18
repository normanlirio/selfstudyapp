package com.teamzmron.selfstudyapp.noun

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.teamzmron.selfstudyapp.LiveDataTestUtil
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.TestUtil
import com.teamzmron.selfstudyapp.WordDatabaseTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class NounDAOTest : WordDatabaseTest() {

    private val TEST_ENGLISH = "Umbrella"
    private val TEST_JAPANESE = "Kasa"
    private val TEST_TIMESTAMP = "07-2020"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    /*
    Insert, Read, delete
     */
    @Test
    internal fun insertReadDelete() {
        var noun = Noun(TestUtil.TEST_NOUN)

        //insert
        getNounDAO().insertNoun(noun).blockingGet()

        //read
        val liveDataTestUtil: LiveDataTestUtil<List<Noun>> =  LiveDataTestUtil()
        val convertedValueToLiveData: LiveData<List<Noun>> = LiveDataReactiveStreams.fromPublisher(getNounDAO().getNoun())
        var insertedNoun = liveDataTestUtil.getValue(convertedValueToLiveData)

        assertNotNull(insertedNoun)

        assertEquals(noun.english, insertedNoun!![0].english)
        assertEquals(noun.japanese, insertedNoun[0].japanese)
        assertEquals(noun.hiragana, insertedNoun[0].hiragana)
        assertEquals(noun.kanji, insertedNoun[0].kanji)
        assertEquals(noun.timestamp, insertedNoun[0].timestamp)

        noun.id = insertedNoun!![0].id
        assertEquals(noun, insertedNoun[0])

        //delete
        getNounDAO().deleteNoun(noun).blockingGet()

        insertedNoun = liveDataTestUtil.getValue(convertedValueToLiveData)

        assertEquals(0, insertedNoun!!.size)

    }

    /*
     Insert, Read, Update, Read, Delete
     */
   @Test
   fun insertReadUpdateReadDelete() {
        var noun = Noun(TestUtil.TEST_NOUN)

        //insert
        getNounDAO().insertNoun(noun).blockingGet()

        //read
        val liveDataTestUtil: LiveDataTestUtil<List<Noun>> =  LiveDataTestUtil()
        val convertedValueToLiveData: LiveData<List<Noun>> = LiveDataReactiveStreams.fromPublisher(getNounDAO().getNoun())
        var insertedNoun = liveDataTestUtil.getValue(convertedValueToLiveData)

        assertNotNull(insertedNoun)

        assertEquals(noun.english, insertedNoun!![0].english)
        assertEquals(noun.japanese, insertedNoun[0].japanese)
        assertEquals(noun.hiragana, insertedNoun[0].hiragana)
        assertEquals(noun.kanji, insertedNoun[0].kanji)
        assertEquals(noun.timestamp, insertedNoun[0].timestamp)

        noun.id = insertedNoun!![0].id
        assertEquals(noun, insertedNoun[0])

        noun.english = TEST_ENGLISH
        noun.japanese = TEST_JAPANESE
        noun.timestamp = TEST_TIMESTAMP

        getNounDAO().updateNoun(noun).blockingGet()

        insertedNoun = liveDataTestUtil.getValue(convertedValueToLiveData)
        assertEquals(noun.english, insertedNoun!![0].english)
        assertEquals(noun.japanese, insertedNoun[0].japanese)
        assertEquals(noun.hiragana, insertedNoun[0].hiragana)
        assertEquals(noun.kanji, insertedNoun[0].kanji)
        assertEquals(noun.timestamp, insertedNoun[0].timestamp)

        noun.id = insertedNoun!![0].id
        assertEquals(noun, insertedNoun[0])

        //delete
        getNounDAO().deleteNoun(noun).blockingGet()

        insertedNoun = liveDataTestUtil.getValue(convertedValueToLiveData)

        assertEquals(0, insertedNoun!!.size)
    }
}























