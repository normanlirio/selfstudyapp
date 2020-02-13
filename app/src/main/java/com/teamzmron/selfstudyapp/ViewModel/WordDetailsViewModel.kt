package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordDetailsViewModel : ViewModel() {
     var mutableId = MutableLiveData<Int>()

    fun setMutableId(id : Int) {
        mutableId.postValue(id)
    }

    fun getMutableId() : LiveData<Int> {
        return mutableId
    }
}