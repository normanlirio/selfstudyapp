package com.teamzmron.selfstudyapp.Room.DAO

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.teamzmron.selfstudyapp.Room.Entity.Word
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WordDAO {
    @Insert(onConflict = REPLACE)
    fun insertWord(word : Word) : Completable

    @Query("SELECT * from words ORDER BY timestamp DESC")
    fun getWords() : Single<List<Word>>

    @Query("SELECT * from words where id IN (:uid)")
    fun getWordById(uid: Int) : Single<List<Word>>

    @Update(onConflict = REPLACE)
    fun updateWord(word: Word) : Completable


    @Delete
    fun deleteWord(word: Word) : Completable

}