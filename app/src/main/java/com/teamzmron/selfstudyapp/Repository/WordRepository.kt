package com.teamzmron.selfstudyapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Word
import com.teamzmron.selfstudyapp.SelfStudyApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WordRepository {
    private val compositeDisposable = CompositeDisposable()
    private fun getDBInstance(): WordDatabase {
        return WordDatabase.getDatabasenIstance(SelfStudyApplication.getAppContext())
    }

    fun getWordRepositoryInstance(): WordRepository {
        return WordRepository()
    }

    fun getWordsFromDB(): MutableLiveData<List<Word>> {
        var list = MutableLiveData<List<Word>>()
        getDBInstance().wordDao().getWords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                list.postValue(it)
            }, { exception ->
                exception.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
        return list
    }

    fun getWordByIdFromDB(id: Int): LiveData<Word> {
        var list = MutableLiveData<Word>()
        getDBInstance().wordDao().getWordById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                list.value = it
            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
        return list
    }

    fun saveWordRepo(word: Word) {
        getDBInstance().wordDao().insertWord(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteWordRepo(word: Word) {
        getDBInstance().wordDao().deleteWord(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteAll() {
        getDBInstance().wordDao().deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }


    fun updateWordRepo(word: Word) {
        getDBInstance().wordDao().updateWord(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({

            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun onClearDisposable() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }


}