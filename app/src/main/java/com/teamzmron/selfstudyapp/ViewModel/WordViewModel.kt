package com.teamzmron.selfstudyapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teamzmron.selfstudyapp.Repository.WordRepository

class WordViewModel : ViewModel() {
    private fun getWordRepoInstance() : WordRepository {
        return WordRepository().getWordRepoInstance()
    }

    fun deleteAllWordsFromRepo() : LiveData<Int> {
        return getWordRepoInstance().deleteAllWords()
    }

    fun deleteAllNoun() : LiveData<Int> {
        return getWordRepoInstance().deleteNouns()
    }

    fun deleteAllVerb() : LiveData<Int> {
        return getWordRepoInstance().deleteVerbs()
    }

    fun deleteAllAdjectives() : LiveData<Int> {
        return getWordRepoInstance().deleteAdjectives()
    }
}