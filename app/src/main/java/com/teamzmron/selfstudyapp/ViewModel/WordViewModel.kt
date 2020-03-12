package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.WordRepository
import com.teamzmron.selfstudyapp.Room.Entity.Noun

class WordViewModel : ViewModel() {

    private fun getWordRepoInstance() : WordRepository {
        return WordRepository().getWordRepositoryInstance()
    }

    fun getWordsFromRepo(): LiveData<List<Noun>> {
        return getWordRepoInstance().getWordsFromDB()
    }

    fun getWordById(id: Int): LiveData<Noun> {
        return getWordRepoInstance().getWordByIdFromDB(id)
    }

    fun saveToDB(noun: Noun) {
        getWordRepoInstance().saveWordRepo(noun)
    }

    fun updateWord(noun: Noun) {
        getWordRepoInstance().updateWordRepo(noun)
    }


    fun deleteWordById(noun: Noun) {
        getWordRepoInstance().deleteWordRepo(noun)
    }

    fun deleteAllWords() {
        getWordRepoInstance().deleteAll()
    }

    override fun onCleared() {
        getWordRepoInstance().onClearDisposable()
        super.onCleared()
    }
    

}

