package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.AdjectiveRepository
import com.teamzmron.selfstudyapp.Repository.VerbRepository
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ui.Resource
import javax.inject.Inject

class AdjectiveViewModel @Inject constructor(private val adjectiveRepository: AdjectiveRepository) : ViewModel() {

    fun observerSaveResult() : LiveData<Resource<Long>> = adjectiveRepository.observeSaveResult()

    fun observeGetDeleteResult() : LiveData<Resource<Int>> = adjectiveRepository.observeDeleteResult()

    fun observeUpdateResult() : LiveData<Resource<Int>> = adjectiveRepository.observeUpdateResult()

    fun getAdjectiveFromRepo(): LiveData<Resource<List<Adjective>>> {
        return adjectiveRepository.getAdjectiveFromDB()
    }

    fun getAdjectiveById(id: Int): LiveData<Adjective> {
        return adjectiveRepository.getAdjectiveByIdFromDB(id)
    }

    fun saveToDB(adj: Adjective)  {
      adjectiveRepository.saveAdjectiveRepo(adj)
    }

    fun updateAdjective(adj: Adjective) {
        adjectiveRepository.updateAdjectiveRepo(adj)
    }


    fun deleteAdjective(adj: Adjective) {
        adjectiveRepository.deleteAdjectiveRepo(adj)
    }


    override fun onCleared() {
        adjectiveRepository.onClearDisposable()
        super.onCleared()
    }

}