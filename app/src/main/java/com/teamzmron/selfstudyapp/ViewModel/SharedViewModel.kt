package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {

    val mutableNoun: MutableLiveData<Noun> = MutableLiveData()
    val mutableAdjective: MutableLiveData<Adjective> = MutableLiveData()
    val mutableVerb: MutableLiveData<Verb> = MutableLiveData()

    fun setMutableNoun(noun: Noun) {
        mutableNoun.value = noun
    }

    fun setMutableAdjective(adjective: Adjective) {
        mutableAdjective.value = adjective
    }

    fun setMutableVerb(verb: Verb) {
        mutableVerb.value = verb
    }

}