package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.VerbRepository
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import javax.inject.Inject

class VerbViewModel @Inject constructor(private val verbRepository: VerbRepository) : ViewModel() {

    fun getVerbsFromRepo(): LiveData<List<Verb>> {
        return verbRepository.getVerbFromDB()
    }

    fun getVerbById(id: Int): LiveData<Verb> {
        return verbRepository.getVerbByIdFromDB(id)
    }

    fun saveToDB(verb: Verb) : LiveData<Long>{
       return verbRepository.saveVerbRepo(verb)
    }

    fun updateVerb(verb: Verb) {
        verbRepository.updateVerbRepo(verb)
    }


    fun deleteVerbById(verb: Verb) {
        verbRepository.deleteVerbRepo(verb)
    }

    override fun onCleared() {
        verbRepository.onClearDisposable()
        super.onCleared()
    }

}