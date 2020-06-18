package com.teamzmron.selfstudyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class LiveDataTestUtil<T> {

    fun <T> getValue(liveData: LiveData<T>) : T? {
        var data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)

        val observer = object : Observer<T> {
            override fun onChanged(t: T) {
                data[0] = t
                latch.countDown() // release the latch
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)

        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (e: InterruptedException) {
            throw InterruptedException("Latch Failure")
        }

        @Suppress("UNCHECKED_CAST")
        if(data.isNotEmpty()) {
            return data[0] as T
        }
        return null
    }


}