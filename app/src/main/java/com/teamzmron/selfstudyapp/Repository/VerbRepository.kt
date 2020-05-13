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

class VerbRepository(private val db: WordDatabase) {
    private val compositeDisposable = CompositeDisposable()

    fun getVerbFromDB(): MediatorLiveData<Resource<List<Verb>>> {
        var list = MediatorLiveData<Resource<List<Verb>>>()
        val source: LiveData<Resource<List<Verb>>> = LiveDataReactiveStreams.fromPublisher(
            db.verbDao().getVerbs()
                .onErrorReturn {
                    var verb = Verb()
                    verb.verbId = -1
                    var verbList = ArrayList<Verb>()
                    verbList.add(verb)
                    verbList
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

        list.addSource(source, Observer {
            list.value = it
            list.removeSource(source)
        })
        return list
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

    fun saveVerbRepo(verb: Verb): MediatorLiveData<Resource<Long>> {
        val result = MediatorLiveData<Resource<Long>>()

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

        result.addSource(source, Observer {
            result.value = it
            result.removeSource(source)
        })
        return result
    }

    fun deleteVerbRepo(verb: Verb) {
        db.verbDao().deleteVerb(verb)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                it.localizedMessage
            }).let {
                compositeDisposable.add(it)
            }
    }


    fun updateVerbRepo(verb: Verb) {
        db.verbDao().updateVerb(verb)
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