package com.teamzmron.selfstudyapp.Repository

import androidx.lifecycle.*
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.di.main.MainScope
import com.teamzmron.selfstudyapp.ui.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@MainScope
class NounRepository(private val db: WordDatabase) {
    private val compositeDisposable = CompositeDisposable()
    private var deleteResult : MediatorLiveData<Resource<Int>> = MediatorLiveData()


    fun getDeleteResult() : LiveData<Resource<Int>> = deleteResult

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

    fun saveNounRepo(noun: Noun): MediatorLiveData<Resource<Long>> {
        var result = MediatorLiveData<Resource<Long>>()

        val source: LiveData<Resource<Long>> = LiveDataReactiveStreams.fromPublisher(
            db.nounDAO().insertNoun(noun)
                .toFlowable()
                .onErrorReturn {
                    val errorLong = (-1).toLong()
                    errorLong
                }
                .map {
                    if (it < 0) {
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

    fun deleteNounRepo(noun: Noun)  {
        deleteResult.value = Resource.loading(null)
        val source: LiveData<Resource<Int>> = LiveDataReactiveStreams.fromPublisher(
            db.nounDAO().deleteNoun(noun)
                .toFlowable()
                .onErrorReturn {
                    -1
                }
                .map {
                    if (it == -1) {
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