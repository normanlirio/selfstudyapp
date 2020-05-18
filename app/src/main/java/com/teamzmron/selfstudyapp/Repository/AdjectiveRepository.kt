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

class AdjectiveRepository(private val db: WordDatabase) : BaseRepository() {
    private val compositeDisposable = CompositeDisposable()
    private var adjList: MediatorLiveData<Resource<List<Adjective>>> = MediatorLiveData()


    fun getAdjectiveFromDB(): LiveData<Resource<List<Adjective>>> {
        val source: LiveData<Resource<List<Adjective>>> = LiveDataReactiveStreams.fromPublisher(
            db.adjDao().getAdjectives()
                .onErrorReturn {
                    var adj = Adjective()
                    adj.adjId = -1
                    var list = ArrayList<Adjective>()
                    list.add(adj)
                    list
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

        adjList.addSource(source, Observer {
            adjList.value = it
            adjList.removeSource(source)
        })
        return adjList
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

    fun saveAdjectiveRepo(adj: Adjective)  {
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

        saveResult.addSource(source, Observer {
            saveResult.value = it
            saveResult.removeSource(source)
        })

    }

    fun deleteAdjectiveRepo(adj: Adjective) {
        val source: LiveData<Resource<Int>> = LiveDataReactiveStreams.fromPublisher(
            db.adjDao().deleteAdjective(adj)
                .toFlowable()
                .onErrorReturn {
                    -1
                }
                .map {
                    if(it < 0) {
                        Resource.error("Something went wrong", null)
                    }

                    Resource.success(it)
                }
                .subscribeOn(Schedulers.io())
        )

        deleteResult.addSource(source, Observer {
            deleteResult.value = it
            deleteResult.removeSource(source)
        })
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