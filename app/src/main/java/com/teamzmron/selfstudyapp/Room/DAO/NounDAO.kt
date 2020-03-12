package com.teamzmron.selfstudyapp.Room.DAO

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import io.reactivex.*

@Dao
interface NounDAO {
    @Insert(onConflict = REPLACE)
    fun insertNoun(noun : Noun) : Single<Long>

    @Query("SELECT * from verb, adjective, noun ORDER BY english ASC")
    fun getNouns() : Flowable<List<Noun>>

    @Query("SELECT * from  verb, adjective, noun where id IN (:uid)")
    fun getNounById(uid: Int) : Maybe<Noun>

    @Update(onConflict = REPLACE)
    fun updateNoun(noun: Noun) : Single<Int>

    @Delete
    fun deleteNoun(noun: Noun) : Single<Int>

    @Query("DELETE from noun")
    fun deleteAll() : Single<Int>
    
}