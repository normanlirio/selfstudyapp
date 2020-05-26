package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.VerbRepository
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ui.Resource
import javax.inject.Inject

class VerbViewModel @Inject constructor(private val verbRepository: VerbRepository) : ViewModel() {

    fun observeGetDeleteResult() : LiveData<Resource<Int>> = verbRepository.observeDeleteResult()

    fun observeSaveResult() : LiveData<Resource<Long>> = verbRepository.observeSaveResult()

    fun observeUpdateResult() : LiveData<Resource<Int>> = verbRepository.observeUpdateResult()

    fun getVerbsFromRepo(): LiveData<Resource<List<Verb>>> {
        return verbRepository.getVerbFromDB()
    }


    fun saveToDB(verb: Verb) {
        verbRepository.saveVerbRepo(verb)
    }

    fun updateVerb(verb: Verb) {
        verbRepository.updateVerbRepo(verb)
    }


    fun deleteVerb(verb: Verb) {
        verbRepository.deleteVerbRepo(verb)
    }

    override fun onCleared() {
        verbRepository.onClearDisposable()
        super.onCleared()
    }

}