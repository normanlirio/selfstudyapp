package com.teamzmron.selfstudyapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.SelfStudyApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NounRepository {
    private val compositeDisposable = CompositeDisposable()
    private fun getDBInstance(): WordDatabase {
        return WordDatabase.getDatabasenIstance(SelfStudyApplication.getAppContext())
    }

    fun getNounRepositoryInstance(): NounRepository {
        return NounRepository()
    }

    fun getNounFromDB(): MutableLiveData<List<Noun>> {
        var list = MutableLiveData<List<Noun>>()
        getDBInstance().nounDAO().getNoun()
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

    fun getNounByIdFromDB(id: Int): LiveData<Noun> {
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

    fun saveNounRepo(noun: Noun) {
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

    fun deleteNounRepo(noun: Noun) {
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

    fun deleteAllNoun() {
        getDBInstance().nounDAO().deleteAllNoun()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }


    fun updateNounRepo(noun: Noun) {
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