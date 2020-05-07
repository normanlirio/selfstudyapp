package com.teamzmron.selfstudyapp.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.SelfStudyApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NounRepository(private val db: WordDatabase) {
    private val compositeDisposable = CompositeDisposable()

    fun getNounFromDB(): MutableLiveData<List<Noun>> {
        var list = MutableLiveData<List<Noun>>()
        db.nounDAO().getNoun()
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
        db.nounDAO().getNounById(id)
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

    fun saveNounRepo(noun: Noun) : MutableLiveData<Long> {
        var result = MutableLiveData<Long>()
        db.nounDAO().insertNoun(noun)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.postValue(it)
                    if(it > 0) {
                        Log.v("Save Noun", "It's a success!")
                    }
            }, {

                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }

        return result
    }

    fun deleteNounRepo(noun: Noun) {
        db.nounDAO().deleteNoun(noun)
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
        db.nounDAO().updateNoun(noun)
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