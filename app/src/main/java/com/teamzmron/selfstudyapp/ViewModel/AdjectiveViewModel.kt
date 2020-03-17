package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.AdjectiveRepository
import com.teamzmron.selfstudyapp.Repository.VerbRepository
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Verb

class AdjectiveViewModel : ViewModel() {
    private fun getAdjectiveRepoInstance() : AdjectiveRepository {
        return AdjectiveRepository().getAdjectiveRepositoryInstance()
    }

    fun getAdjectiveFromRepo(): LiveData<List<Adjective>> {
        return getAdjectiveRepoInstance().getAdjectiveFromDB()
    }

    fun getAdjectiveById(id: Int): LiveData<Adjective> {
        return getAdjectiveRepoInstance().getAdjectiveByIdFromDB(id)
    }

    fun saveToDB(adj: Adjective) : LiveData<Long> {
      return  getAdjectiveRepoInstance().saveAdjectiveRepo(adj)
    }

    fun updateAdjective(adj: Adjective) {
        getAdjectiveRepoInstance().updateAdjectiveRepo(adj)
    }


    fun deleteAdjectiveById(adj: Adjective) {
        getAdjectiveRepoInstance().deleteAdjectiveRepo(adj)
    }

    fun deleteAllAdjectives() {
        getAdjectiveRepoInstance().deleteAllAdjective()
    }

    override fun onCleared() {
        getAdjectiveRepoInstance().onClearDisposable()
        super.onCleared()
    }

}