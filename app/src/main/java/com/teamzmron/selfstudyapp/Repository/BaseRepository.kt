package com.teamzmron.selfstudyapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.teamzmron.selfstudyapp.ui.Resource

open class BaseRepository {
     var deleteResult : MediatorLiveData<Resource<Int>> = MediatorLiveData()
     var saveResult: MediatorLiveData<Resource<Long>> = MediatorLiveData()
     var updateresult: MediatorLiveData<Resource<Int>> = MediatorLiveData()

     fun observeDeleteResult() : LiveData<Resource<Int>> = deleteResult

     fun observeSaveResult() : LiveData<Resource<Long>> = saveResult

     fun observeUpdateResult() : LiveData<Resource<Int>> = updateresult

}