package com.teamzmron.selfstudyapp.ViewModel.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.teamzmron.selfstudyapp.LiveDataTestUtil
import com.teamzmron.selfstudyapp.Repository.NounRepository
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.TestUtil
import com.teamzmron.selfstudyapp.TestUtil.Companion.TEST_NOUN
import com.teamzmron.selfstudyapp.ViewModel.util.InstantExecutorExtension
import com.teamzmron.selfstudyapp.ui.Resource
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.lang.Exception

@ExtendWith(InstantExecutorExtension::class)
class NounRepositoryTest {

    @Mock
    private lateinit var nounRepository: NounRepository

    private val noun = Noun(TEST_NOUN)

    @Mock
    private lateinit var wordDatabase: WordDatabase

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)

    }

    @Test
    internal fun testNounRepositoryNotNull() {
        assertNotNull(nounRepository)
    }

    @Test
    fun getNoun_returnList() {
        val noun: List<Noun> = TestUtil.TEST_NOUN_LIST
        var liveDataTestUtil: LiveDataTestUtil<List<Noun>> =  LiveDataTestUtil()
        var returnedData : MutableLiveData<Resource<List<Noun>>> = MutableLiveData()
        var wrappedNoun : Resource<List<Noun>> = Resource.success(noun)
        returnedData.value = wrappedNoun
        `when`(nounRepository.observeNounList()).thenReturn(returnedData)

        val observedValue = liveDataTestUtil.getValue(nounRepository.observeNounList())

        assertEquals(wrappedNoun.data, observedValue!!.data)

    }

    @Test
    fun getNoun_returnEmptyList() {
        val noun: List<Noun> = ArrayList()
        val liveDataTestUtil: LiveDataTestUtil<List<Noun>> =  LiveDataTestUtil()
        val returnedData : MutableLiveData<Resource<List<Noun>>> = MutableLiveData()
        val wrappedNoun : Resource<List<Noun>> = Resource.success(noun)
        returnedData.value = wrappedNoun
        `when`(nounRepository.observeNounList()).thenReturn(returnedData)

        val observedValue = liveDataTestUtil.getValue(nounRepository.observeNounList())

        assertEquals(wrappedNoun.data, observedValue!!.data)
    }

    @Test
    fun deleteNoun_returnSuccess() {
        var liveDataTestUtil: LiveDataTestUtil<Resource<Int>> = LiveDataTestUtil()
        val successResponse: Resource<Int> = Resource.success(1)
        val returnedData : MutableLiveData<Resource<Int>> = MutableLiveData()
        returnedData.value = successResponse
        `when`(nounRepository.observeDeleteResult()).thenReturn(returnedData)

        val observedValue = liveDataTestUtil.getValue(nounRepository.observeDeleteResult())

        assertEquals(successResponse.data, observedValue!!.data)

    }

    @Test
    fun deleteNoun_throwException() {
        assertThrows<Exception> {
            val noun: Noun? = null
            nounRepository.deleteNounRepo(noun!!)
        }
    }

    @Test
    fun updateNoun_returnSuccess() {
        var liveDataTestUtil: LiveDataTestUtil<Resource<Int>> = LiveDataTestUtil()
        val successResponse: Resource<Int> = Resource.success(1)
        val returnedData : MutableLiveData<Resource<Int>> = MutableLiveData()
        returnedData.value = successResponse
        `when`(nounRepository.observeUpdateResult()).thenReturn(returnedData)

        val observedValue = liveDataTestUtil.getValue(nounRepository.observeUpdateResult())

        assertEquals(successResponse.data, observedValue!!.data)

    }
}