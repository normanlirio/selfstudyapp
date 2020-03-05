package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.WordRepository
import com.teamzmron.selfstudyapp.Room.Entity.Word

class WordViewModel : ViewModel() {

    private fun getWordRepoInstance() : WordRepository {
        return WordRepository().getWordRepositoryInstance()
    }

    fun getWordsFromRepo(): LiveData<List<Word>> {
        return getWordRepoInstance().getWordsFromDB()
    }

    fun getWordById(id: Int): LiveData<Word> {
        return getWordRepoInstance().getWordByIdFromDB(id)
    }

    fun saveToDB(word: Word) {
        getWordRepoInstance().saveWordRepo(word)
    }

    fun updateWord(word: Word) {
        getWordRepoInstance().updateWordRepo(word)
    }


    fun deleteWordById(word: Word) {
        getWordRepoInstance().deleteWordRepo(word)
    }

    fun deleteAllWords() {
        getWordRepoInstance().deleteAll()
    }

    override fun onCleared() {
        getWordRepoInstance().onClearDisposable()
        super.onCleared()
    }
    

}

