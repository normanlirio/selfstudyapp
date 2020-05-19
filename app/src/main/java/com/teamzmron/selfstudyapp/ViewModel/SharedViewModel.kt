package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ui.Resource
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {

     val mutableNoun: MutableLiveData<Noun> = MutableLiveData()

    fun setMutableNoun(noun : Noun) {
        mutableNoun.value = noun
    }

}