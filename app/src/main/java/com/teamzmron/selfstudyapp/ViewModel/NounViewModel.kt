package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.NounRepository
import com.teamzmron.selfstudyapp.Room.Entity.Noun

class NounViewModel : ViewModel() {

    private fun getNounRepoInstance() : NounRepository {
        return NounRepository().getNounRepositoryInstance()
    }

    fun getWordsFromRepo(): LiveData<List<Noun>> {
        return getNounRepoInstance().getNounFromDB()
    }

    fun getWordById(id: Int): LiveData<Noun> {
        return getNounRepoInstance().getNounByIdFromDB(id)
    }

    fun saveToDB(noun: Noun) : LiveData<Long> {
        return getNounRepoInstance().saveNounRepo(noun)
    }

    fun updateWord(noun: Noun) {
        getNounRepoInstance().updateNounRepo(noun)
    }


    fun deleteWordById(noun: Noun) {
        getNounRepoInstance().deleteNounRepo(noun)
    }


    override fun onCleared() {
        getNounRepoInstance().onClearDisposable()
        super.onCleared()
    }
    

}

