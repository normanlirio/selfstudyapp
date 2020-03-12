package com.teamzmron.selfstudyapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.SelfStudyApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AdjectiveRepository {
    private val compositeDisposable = CompositeDisposable()
    private fun getDBInstance(): WordDatabase {
        return WordDatabase.getDatabasenIstance(SelfStudyApplication.getAppContext())
    }

    fun getAdjectiveRepositoryInstance(): AdjectiveRepository {
        return AdjectiveRepository()
    }

    fun getAdjectiveFromDB(): MutableLiveData<List<Adjective>> {
        var list = MutableLiveData<List<Adjective>>()
        getDBInstance().adjDao().getAdjectives()
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

    fun getAdjectiveByIdFromDB(id: Int): LiveData<Adjective> {
        var list = MutableLiveData<Adjective>()
        getDBInstance().adjDao().getAdjectiveById(id)
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

    fun saveAdjectiveRepo(adj: Adjective) {
        getDBInstance().adjDao().insertAdjective(adj)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteAdjectiveRepo(adj: Adjective) {
        getDBInstance().adjDao().deleteAdjective(adj)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteAllAdjective() {
        getDBInstance().adjDao().deleteAllAdjectives()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }


    fun updateAdjectiveRepo(adj: Adjective) {
        getDBInstance().adjDao().updateAdjective(adj)
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