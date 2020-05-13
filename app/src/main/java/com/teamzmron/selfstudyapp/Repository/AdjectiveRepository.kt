package com.teamzmron.selfstudyapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.SelfStudyApplication
import com.teamzmron.selfstudyapp.ui.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AdjectiveRepository(private val db: WordDatabase) {
    private val compositeDisposable = CompositeDisposable()


    fun getAdjectiveFromDB(): MediatorLiveData<Resource<List<Adjective>>> {
        var list = MediatorLiveData<Resource<List<Adjective>>>()
        val source: LiveData<Resource<List<Adjective>>> = LiveDataReactiveStreams.fromPublisher(
            db.adjDao().getAdjectives()
                .onErrorReturn {
                    var adj = Adjective()
                    adj.adjId = -1
                    var adjList = ArrayList<Adjective>()
                    adjList.add(adj)
                    adjList
                }
                .map {
                    if (it.isNotEmpty()) {
                        if (it[0].adjId == -1) {
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

    fun getAdjectiveByIdFromDB(id: Int): LiveData<Adjective> {
        var list = MutableLiveData<Adjective>()
        db.adjDao().getAdjectiveById(id)
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

    fun saveAdjectiveRepo(adj: Adjective) : MediatorLiveData<Resource<Long>> {
        var result = MediatorLiveData<Resource<Long>>()
        val source: LiveData<Resource<Long>> = LiveDataReactiveStreams.fromPublisher(
            db.adjDao().insertAdjective(adj)
                .toFlowable()
                .onErrorReturn {
                    val errorLong = (-1).toLong()
                    errorLong
                }
                .map {
                    if(it < 0) {
                        Resource.error("Something went wrong", null)
                    }

                    Resource.success(it)
                }
                .subscribeOn(Schedulers.io())
        )

        result.addSource(source, Observer {
            result.value = it
            result.removeSource(source)
        })
        return result
    }

    fun deleteAdjectiveRepo(adj: Adjective) {
        db.adjDao().deleteAdjective(adj)
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
        db.adjDao().updateAdjective(adj)
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