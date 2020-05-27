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
class NounRepository(private val db: WordDatabase)  : BaseRepository() {
    private val compositeDisposable = CompositeDisposable()
    private var nounList: MediatorLiveData<Resource<List<Noun>>> = MediatorLiveData()


    fun getNounFromDB() : LiveData<Resource<List<Noun>>> {
        nounList.value = Resource.loading(null)
        val source: LiveData<Resource<List<Noun>>> = LiveDataReactiveStreams.fromPublisher(
            db.nounDAO().getNoun()
                .onErrorReturn {
                    var noun = Noun()
                    noun.id = -1
                    var list = ArrayList<Noun>()
                    list.add(noun)
                    list
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

        nounList.addSource(source, Observer {
            nounList.value = it
            nounList.removeSource(source)
        })
        return nounList
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

    fun saveNounRepo(noun: Noun) {
        saveResult.value = Resource.loading(null)
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

        saveResult.addSource(source, Observer {
            saveResult.value = it
            saveResult.removeSource(source)
        })
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
        updateresult.value = Resource.loading(null)
        val source: LiveData<Resource<Int>> = LiveDataReactiveStreams.fromPublisher(
            db.nounDAO().updateNoun(noun)
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
        updateresult.addSource(source, Observer {
            updateresult.value = it
            updateresult.removeSource(source)
        })
    }

    fun onClearDisposable() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }


}