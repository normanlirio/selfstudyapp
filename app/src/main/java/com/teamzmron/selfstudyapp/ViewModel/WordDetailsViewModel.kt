package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordDetailsViewModel : ViewModel() {
    private var mutableId = MutableLiveData<Int>()

    fun setMutableId(id : Int) {
        mutableId.value = id
    }

    fun getMutableId() : LiveData<Int> {
        return mutableId
    }
}