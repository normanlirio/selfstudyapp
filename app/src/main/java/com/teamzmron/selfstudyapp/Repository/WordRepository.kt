package com.teamzmron.selfstudyapp.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.SelfStudyApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WordRepository {

    private val compositeDisposable = CompositeDisposable()
    private fun getDBInstance(): WordDatabase {
        return WordDatabase.getDatabaseInstance(SelfStudyApplication.getAppContext())
    }

    fun getWordRepoInstance(): WordRepository {
        return WordRepository()
    }


    fun deleteAllWords(): MutableLiveData<Int> {
        var result = MutableLiveData<Int>()
        try {
            deleteNouns()
            deleteVerbs()
            deleteAdjectives()

        } catch (e: Exception) {
            result.postValue(0)
            Log.v("ERROR", "ERROR ${e.localizedMessage} ")
        }
        return result
    }


     fun deleteNouns(): MutableLiveData<Int> {
        var result = MutableLiveData<Int>()
        getDBInstance().wordDAO().deleteAllNoun()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.postValue(it)
            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.addAll(it)
            }
        return result
    }

     fun deleteVerbs(): MutableLiveData<Int> {
        var result = MutableLiveData<Int>()
        getDBInstance().wordDAO().deleteAllVerbs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.postValue(it)
            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.addAll(it)
            }
        return result
    }

    fun deleteAdjectives(): MutableLiveData<Int> {
        var result = MutableLiveData<Int>()
        getDBInstance().wordDAO().deleteAllAdjectives()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.postValue(it)
            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.addAll(it)
            }
        return result
    }

}