package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.NounRepository
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ui.Resource
import javax.inject.Inject

class NounViewModel @Inject constructor(private val nounRepository: NounRepository) : ViewModel() {

    fun observeGetDeleteResult() : LiveData<Resource<Int>> = nounRepository.observeDeleteResult()

    fun observeSaveResult() : LiveData<Resource<Long>> = nounRepository.observeSaveResult()

    fun getNounsFromRepo()  : LiveData<Resource<List<Noun>>>{
       return  nounRepository.getNounFromDB()
    }

    fun getNounById(id: Int): LiveData<Noun> {
        return nounRepository.getNounByIdFromDB(id)
    }

    fun saveToDB(noun: Noun)  {
       nounRepository.saveNounRepo(noun)
    }

    fun updateNoun(noun: Noun) {
        nounRepository.updateNounRepo(noun)
    }


    fun deleteNoun(noun: Noun)  {
       nounRepository.deleteNounRepo(noun)
    }


    override fun onCleared() {
        nounRepository.onClearDisposable()
        super.onCleared()
    }
    

}

