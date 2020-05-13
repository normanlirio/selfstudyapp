package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.NounRepository
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ui.Resource
import javax.inject.Inject

class NounViewModel @Inject constructor(private val nounRepository: NounRepository) : ViewModel() {

    fun getWordsFromRepo(): LiveData<Resource<List<Noun>>> {
        return nounRepository.getNounFromDB()
    }

    fun getWordById(id: Int): LiveData<Noun> {
        return nounRepository.getNounByIdFromDB(id)
    }

    fun saveToDB(noun: Noun) : LiveData<Resource<Long>> {
        return nounRepository.saveNounRepo(noun)
    }

    fun updateWord(noun: Noun) {
        nounRepository.updateNounRepo(noun)
    }


    fun deleteWordById(noun: Noun) {
        nounRepository.deleteNounRepo(noun)
    }


    override fun onCleared() {
        nounRepository.onClearDisposable()
        super.onCleared()
    }
    

}

