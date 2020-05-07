package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.AdjectiveRepository
import com.teamzmron.selfstudyapp.Repository.VerbRepository
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import javax.inject.Inject

class AdjectiveViewModel @Inject constructor(private val adjectiveRepository: AdjectiveRepository) : ViewModel() {

    fun getAdjectiveFromRepo(): LiveData<List<Adjective>> {
        return adjectiveRepository.getAdjectiveFromDB()
    }

    fun getAdjectiveById(id: Int): LiveData<Adjective> {
        return adjectiveRepository.getAdjectiveByIdFromDB(id)
    }

    fun saveToDB(adj: Adjective) : LiveData<Long> {
      return  adjectiveRepository.saveAdjectiveRepo(adj)
    }

    fun updateAdjective(adj: Adjective) {
        adjectiveRepository.updateAdjectiveRepo(adj)
    }


    fun deleteAdjectiveById(adj: Adjective) {
        adjectiveRepository.deleteAdjectiveRepo(adj)
    }


    override fun onCleared() {
        adjectiveRepository.onClearDisposable()
        super.onCleared()
    }

}