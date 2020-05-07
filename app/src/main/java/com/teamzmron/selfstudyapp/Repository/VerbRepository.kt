package com.teamzmron.selfstudyapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.SelfStudyApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class VerbRepository(private val wordDatabase: WordDatabase) {
    private val compositeDisposable = CompositeDisposable()

    fun getVerbFromDB(): MutableLiveData<List<Verb>> {
        var list = MutableLiveData<List<Verb>>()
        wordDatabase.verbDao().getVerbs()
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

    fun getVerbByIdFromDB(id: Int): MutableLiveData<Verb> {
        var list = MutableLiveData<Verb>()
        wordDatabase.verbDao().getVerbById(id)
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

    fun saveVerbRepo(verb: Verb): MutableLiveData<Long> {
        val result = MutableLiveData<Long>()
        wordDatabase.verbDao().insertVerb(verb)
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

    fun deleteVerbRepo(verb: Verb) {
        wordDatabase.verbDao().deleteVerb(verb)
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
        wordDatabase.verbDao().updateVerb(verb)
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