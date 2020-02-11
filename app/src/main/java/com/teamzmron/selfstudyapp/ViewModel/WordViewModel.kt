package com.teamzmron.selfstudyapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Word
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WordViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var wordList = MutableLiveData<List<Word>>()
    private var wordDatabaseInstance: WordDatabase? = null
    private var wordDetails = MutableLiveData<List<Word>>()

    fun setInstanceOfDB(dbInstance: WordDatabase) {
        this.wordDatabaseInstance = dbInstance
    }

    fun  init() {
        if(wordList != null) {
            return
        }
        wordList = MutableLiveData<List<Word>>()
        displayWordsToList()

    }


    fun displayWordsToList() : LiveData<List<Word>> {
        getWordsFromDB()
        return wordList
    }

    fun getWordById(id : Int) : LiveData<List<Word>> {
        getWordByIdFromDB(id)
        return wordDetails
    }

    fun saveToDB(word: Word) {
        wordDatabaseInstance?.wordDao()?.insertWord(word)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                displayWordsToList()
            }, {

                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun updateWord(word: Word) {
        wordDatabaseInstance?.wordDao()?.updateWord(word)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                displayWordsToList()
            }.let {
                compositeDisposable.add(it)
            }
    }

   private fun getWordsFromDB() {
        wordDatabaseInstance?.wordDao()?.getWords()!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                wordList.postValue(it)
            }, {exception ->
                exception.localizedMessage
            }).let {
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





    private fun getWordByIdFromDB(id : Int) {
        wordDatabaseInstance?.wordDao()?.getWordById(id)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                wordDetails.postValue(it)
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

