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
import javax.inject.Inject

class VerbRepository(private val db: WordDatabase) : BaseRepository() {
    private val compositeDisposable = CompositeDisposable()
    private var verbList: MediatorLiveData<Resource<List<Verb>>> = MediatorLiveData()

    fun getVerbFromDB(): MediatorLiveData<Resource<List<Verb>>> {
        verbList.value = Resource.loading(null)
        val source: LiveData<Resource<List<Verb>>> = LiveDataReactiveStreams.fromPublisher(
            db.verbDao().getVerbs()
                .onErrorReturn {
                    var verb = Verb()
                    verb.verbId = -1
                    var list = ArrayList<Verb>()
                    list.add(verb)
                    list
                }
                .map {
                    if (it.isNotEmpty()) {
                        if (it[0].verbId == -1) {
                            Resource.error("Something went wrong", null)
                        }
                    }
                    Resource.success(it)
                }
                .subscribeOn(Schedulers.io())
        )

        verbList.addSource(source, Observer {
            verbList.value = it
            verbList.removeSource(source)
        })
        return verbList
    }

    fun getVerbByIdFromDB(id: Int): MutableLiveData<Verb> {
        var list = MutableLiveData<Verb>()
        db.verbDao().getVerbById(id)
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

    fun saveVerbRepo(verb: Verb) {
    saveResult.value = Resource.loading(null)
        val source: LiveData<Resource<Long>> = LiveDataReactiveStreams.fromPublisher(
            db.verbDao().insertVerb(verb)
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

    fun deleteVerbRepo(verb: Verb) {
        deleteResult.value = Resource.loading(null)
        val source: LiveData<Resource<Int>> = LiveDataReactiveStreams.fromPublisher(
            db.verbDao().deleteVerb(verb)
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


    fun updateVerbRepo(verb: Verb) {
        updateresult.value = Resource.loading(null)
        val source: LiveData<Resource<Int>> = LiveDataReactiveStreams.fromPublisher(
            db.verbDao().updateVerb(verb)
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