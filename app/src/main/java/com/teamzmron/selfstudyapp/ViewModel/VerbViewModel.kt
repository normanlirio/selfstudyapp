package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.VerbRepository
import com.teamzmron.selfstudyapp.Room.Entity.Verb

class VerbViewModel : ViewModel() {

    private fun getVerbRepoInstance() : VerbRepository {
        return VerbRepository().getVerbRepositoryInstance()
    }

    fun getVerbsFromRepo(): LiveData<List<Verb>> {
        return getVerbRepoInstance().getVerbFromDB()
    }

    fun getVerbById(id: Int): LiveData<Verb> {
        return getVerbRepoInstance().getVerbByIdFromDB(id)
    }

    fun saveToDB(verb: Verb) {
        getVerbRepoInstance().saveVerbRepo(verb)
    }

    fun updateVerb(verb: Verb) {
        getVerbRepoInstance().updateVerbRepo(verb)
    }


    fun deleteVerbById(verb: Verb) {
        getVerbRepoInstance().deleteVerbRepo(verb)
    }

    fun deleteAllVerbs() {
        getVerbRepoInstance().deleteAllVerbs()
    }

    override fun onCleared() {
        getVerbRepoInstance().onClearDisposable()
        super.onCleared()
    }

}