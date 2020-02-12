package com.teamzmron.selfstudyapp.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Word
import com.teamzmron.selfstudyapp.SelfStudyApplication
import com.teamzmron.selfstudyapp.ViewModel.WordViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WordRepository {
    private val compositeDisposable = CompositeDisposable()
    private fun getDBInstance() : WordDatabase {
        val dbInstance = WordDatabase.getDatabasenIstance(SelfStudyApplication.getAppContext())
        return dbInstance
    }

    fun getWordRepositoryInstance() : WordRepository {
        return WordRepository()
    }


     fun getWordsFromDB() : MutableLiveData<List<Word>> {
        var list = MutableLiveData<List<Word>>()
        getDBInstance().wordDao().getWords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
              list.postValue(it)
            }, {exception ->
                exception.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
        return list
    }

     fun getWordByIdFromDB(id : Int) : LiveData<List<Word>> {
         var list = MutableLiveData<List<Word>>()
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

    fun saveWordToDBRepo(word : Word) {
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

}