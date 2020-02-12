package com.teamzmron.selfstudyapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.WordRepository
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Word
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WordViewModel : ViewModel() {
    private var wordRepository: WordRepository? = null
    private val compositeDisposable = CompositeDisposable()
    private var wordList = MutableLiveData<List<Word>>()
    private var wordDatabaseInstance: WordDatabase? = null
    private var wordDetails = MutableLiveData<List<Word>>()

    fun setInstanceOfDB(dbInstance: WordDatabase) {
        this.wordDatabaseInstance = dbInstance
    }

    fun init() {
        wordList = MutableLiveData<List<Word>>()

    }

    fun getWordsFromRepo(): LiveData<List<Word>> {
        wordList.postValue(WordRepository().getWordsFromDB().value)
        return wordList
    }

    fun getWordById(id: Int): LiveData<List<Word>> {
        return WordRepository().getWordRepositoryInstance().getWordByIdFromDB(id)
    }

    fun saveToDB(word: Word) {
        wordRepository = WordRepository().getWordRepositoryInstance()
        wordRepository!!.saveWordToDBRepo(word)
    }

    fun updateWord(word: Word) {
        wordDatabaseInstance?.wordDao()?.updateWord(word)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                // displayWordsToList()
            }.let {
                compositeDisposable.add(it)
            }
    }


    fun deleteWordById(word: Word) {
        wordDatabaseInstance?.wordDao()?.deleteWord(word)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
    

}

