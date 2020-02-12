package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.WordRepository
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Word
import io.reactivex.disposables.CompositeDisposable

class WordViewModel : ViewModel() {
    private var wordRepository: WordRepository? = null
    private val compositeDisposable = CompositeDisposable()
    private var wordList = MutableLiveData<List<Word>>()
    private var wordDatabaseInstance: WordDatabase? = null
    private var wordDetails = MutableLiveData<List<Word>>()

    fun setInstanceOfDB(dbInstance: WordDatabase) {
        this.wordDatabaseInstance = dbInstance
    }

    fun getWordRepoInstance() : WordRepository {
        return WordRepository().getWordRepositoryInstance()
    }

    fun getWordsFromRepo(): LiveData<List<Word>> {
        return getWordRepoInstance().getWordsFromDB()
    }

    fun getWordById(id: Int): LiveData<List<Word>> {
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


    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
    

}

