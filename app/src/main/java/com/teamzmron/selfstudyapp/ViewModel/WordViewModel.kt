package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.NounRepository
import com.teamzmron.selfstudyapp.Room.Entity.Noun

class WordViewModel : ViewModel() {

    private fun getWordRepoInstance() : NounRepository {
        return NounRepository().getNounRepositoryInstance()
    }

    fun getWordsFromRepo(): LiveData<List<Noun>> {
        return getWordRepoInstance().getNounFromDB()
    }

    fun getWordById(id: Int): LiveData<Noun> {
        return getWordRepoInstance().getNounByIdFromDB(id)
    }

    fun saveToDB(noun: Noun) {
        getWordRepoInstance().saveNounRepo(noun)
    }

    fun updateWord(noun: Noun) {
        getWordRepoInstance().updateNounRepo(noun)
    }


    fun deleteWordById(noun: Noun) {
        getWordRepoInstance().deleteNounRepo(noun)
    }

    fun deleteAllWords() {
        getWordRepoInstance().deleteAllNoun()
    }

    override fun onCleared() {
        getWordRepoInstance().onClearDisposable()
        super.onCleared()
    }
    

}

