package com.teamzmron.selfstudyapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Noun
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

    fun getWordsFromDB(): MutableLiveData<List<Noun>> {
        var list = MutableLiveData<List<Noun>>()
        getDBInstance().nounDAO().getNouns()
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

    fun getWordByIdFromDB(id: Int): LiveData<Noun> {
        var list = MutableLiveData<Noun>()
        getDBInstance().nounDAO().getNounById(id)
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

    fun saveWordRepo(noun: Noun) {
        getDBInstance().nounDAO().insertNoun(noun)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteWordRepo(noun: Noun) {
        getDBInstance().nounDAO().deleteNoun(noun)
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
        getDBInstance().nounDAO().deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }


    fun updateWordRepo(noun: Noun) {
        getDBInstance().nounDAO().updateNoun(noun)
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