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

class AdjectiveRepository(private val wordDatabase: WordDatabase) {
    private val compositeDisposable = CompositeDisposable()


    fun getAdjectiveFromDB(): MutableLiveData<List<Adjective>> {
        var list = MutableLiveData<List<Adjective>>()
        wordDatabase.adjDao().getAdjectives()
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
        wordDatabase.adjDao().getAdjectiveById(id)
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

    fun saveAdjectiveRepo(adj: Adjective) : MutableLiveData<Long> {
        var result = MutableLiveData<Long>()
        wordDatabase.adjDao().insertAdjective(adj)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.postValue(it)
            }, {

                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
        return result
    }

    fun deleteAdjectiveRepo(adj: Adjective) {
        wordDatabase.adjDao().deleteAdjective(adj)
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
        wordDatabase.adjDao().updateAdjective(adj)
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