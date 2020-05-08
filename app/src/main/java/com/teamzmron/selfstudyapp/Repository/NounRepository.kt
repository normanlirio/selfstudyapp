package com.teamzmron.selfstudyapp.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ui.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NounRepository(private val db: WordDatabase) {
    private val compositeDisposable = CompositeDisposable()


    fun getNounFromDB(): MediatorLiveData<Resource<List<Noun>>> {

        var list = MediatorLiveData<Resource<List<Noun>>>()
        val source: LiveData<Resource<List<Noun>>> = LiveDataReactiveStreams.fromPublisher(
            db.nounDAO().getNoun()
                .onErrorReturn {
                    var noun = Noun()
                    noun.id = -1
                    var nounList = ArrayList<Noun>()
                    nounList.add(noun)
                    nounList
                }
                .map {
                    if (it.isNotEmpty()) {
                        if (it[0].id == -1) {
                            Resource.error("Something went wrong", null)
                        }
                    }
                    Resource.success(it)
                }
                .subscribeOn(Schedulers.io())
        )

        list.addSource(source, Observer {
            list.value = it
            list.removeSource(source)
        })
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

    fun saveNounRepo(noun: Noun): MutableLiveData<Long> {
        var result = MutableLiveData<Long>()
        db.nounDAO().insertNoun(noun)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.postValue(it)
                if (it > 0) {
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
            .subscribe({

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