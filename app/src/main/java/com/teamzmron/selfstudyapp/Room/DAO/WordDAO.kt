package com.teamzmron.selfstudyapp.Room.DAO

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.teamzmron.selfstudyapp.Room.Entity.Word
import io.reactivex.*

@Dao
interface WordDAO {
    @Insert(onConflict = REPLACE)
    fun insertWord(word : Word) : Single<Long>

    @Query("SELECT * from words ORDER BY english ASC")
    fun getWords() : Flowable<List<Word>>

    @Query("SELECT * from words where id IN (:uid)")
    fun getWordById(uid: Int) : Maybe<Word>

    @Update(onConflict = REPLACE)
    fun updateWord(word: Word) : Single<Int>


    @Delete
    fun deleteWord(word: Word) : Single<Int>

}