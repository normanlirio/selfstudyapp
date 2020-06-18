package com.teamzmron.selfstudyapp.verb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.teamzmron.selfstudyapp.LiveDataTestUtil
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.TestUtil
import com.teamzmron.selfstudyapp.WordDatabaseTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class VerbDAOTest : WordDatabaseTest() {

    private val TEST_ENGLISH = "Drink"
    private val TEST_JAPANESE = "Nomu"
    private val TEST_TIMESTAMP = "07-2020"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    /*
    Insert, Read, delete
     */
    @Test
    internal fun insertReadDelete() {
        var verb = Verb(TestUtil.TEST_VERB)

        //insert
        getVerbDAO().insertVerb(verb).blockingGet()

        //read
        val liveDataTestUtil: LiveDataTestUtil<List<Verb>> =  LiveDataTestUtil()
        val convertedValueToLiveData: LiveData<List<Verb>> = LiveDataReactiveStreams.fromPublisher(getVerbDAO().getVerbs())
        var insertedVerb = liveDataTestUtil.getValue(convertedValueToLiveData)

        Assert.assertNotNull(insertedVerb)
        Assert.assertEquals(verb.englishWord, insertedVerb!![0].englishWord)
        Assert.assertEquals(verb.verbType, insertedVerb!![0].verbType)
        Assert.assertEquals(verb.japaneseWord, insertedVerb[0].japaneseWord)
        Assert.assertEquals(verb.hiraganaForm, insertedVerb[0].hiraganaForm)
        Assert.assertEquals(verb.kanjiForm, insertedVerb[0].kanjiForm)
        Assert.assertEquals(verb.currentTimestamp, insertedVerb[0].currentTimestamp)
        Assert.assertEquals(verb.masu, insertedVerb[0].masu)
        Assert.assertEquals(verb.masuNegative, insertedVerb[0].masuNegative)
        Assert.assertEquals(verb.masuPast, insertedVerb[0].masuPast)
        Assert.assertEquals(verb.masuPastNegative, insertedVerb[0].masuPastNegative)

        verb.verbId = insertedVerb!![0].verbId
        Assert.assertEquals(verb, insertedVerb[0])

        //delete
        getVerbDAO().deleteVerb(verb).blockingGet()

        insertedVerb = liveDataTestUtil.getValue(convertedValueToLiveData)

        Assert.assertEquals(0, insertedVerb!!.size)

    }

    /*
     Insert, Read, Update, Read, Delete
     */
    @Test
    fun insertReadUpdateReadDelete() {
        var verb = Verb(TestUtil.TEST_VERB)

        //insert
        getVerbDAO().insertVerb(verb).blockingGet()

        //read
        val liveDataTestUtil: LiveDataTestUtil<List<Verb>> =  LiveDataTestUtil()
        val convertedValueToLiveData: LiveData<List<Verb>> = LiveDataReactiveStreams.fromPublisher(getVerbDAO().getVerbs())
        var insertedVerb = liveDataTestUtil.getValue(convertedValueToLiveData)

        Assert.assertNotNull(insertedVerb)

        Assert.assertEquals(verb.english, insertedVerb!![0].english)
        Assert.assertEquals(verb.verbType, insertedVerb!![0].verbType)
        Assert.assertEquals(verb.japanese, insertedVerb[0].japanese)
        Assert.assertEquals(verb.hiragana, insertedVerb[0].hiragana)
        Assert.assertEquals(verb.kanji, insertedVerb[0].kanji)
        Assert.assertEquals(verb.timestamp, insertedVerb[0].timestamp)
        Assert.assertEquals(verb.masu, insertedVerb[0].masu)
        Assert.assertEquals(verb.masuNegative, insertedVerb[0].masuNegative)
        Assert.assertEquals(verb.masuPast, insertedVerb[0].masuPast)
        Assert.assertEquals(verb.masuPastNegative, insertedVerb[0].masuPastNegative)


        verb.verbId = insertedVerb!![0].verbId
        Assert.assertEquals(verb, insertedVerb[0])

        verb.english = TEST_ENGLISH
        verb.japanese = TEST_JAPANESE
        verb.timestamp = TEST_TIMESTAMP

        getVerbDAO().updateVerb(verb).blockingGet()

        insertedVerb = liveDataTestUtil.getValue(convertedValueToLiveData)
        Assert.assertEquals(verb.english, insertedVerb!![0].english)
        Assert.assertEquals(verb.japanese, insertedVerb[0].japanese)
        Assert.assertEquals(verb.hiragana, insertedVerb[0].hiragana)
        Assert.assertEquals(verb.kanji, insertedVerb[0].kanji)
        Assert.assertEquals(verb.timestamp, insertedVerb[0].timestamp)
        Assert.assertEquals(verb.masu, insertedVerb[0].masu)
        Assert.assertEquals(verb.masuNegative, insertedVerb[0].masuNegative)
        Assert.assertEquals(verb.masuPast, insertedVerb[0].masuPast)
        Assert.assertEquals(verb.masuPastNegative, insertedVerb[0].masuPastNegative)


        verb.verbId = insertedVerb!![0].verbId
        Assert.assertEquals(verb, insertedVerb[0])

        //delete
        getVerbDAO().deleteVerb(verb).blockingGet()

        insertedVerb = liveDataTestUtil.getValue(convertedValueToLiveData)

        Assert.assertEquals(0, insertedVerb!!.size)
    }
}