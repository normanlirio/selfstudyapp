package com.teamzmron.selfstudyapp.Room.DAO

import androidx.room.*
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface VerbDAO  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVerb(verb : Verb) : Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInitialVerb(verb : Verb)

    @Query("SELECT * from verb ORDER BY english ASC")
    fun getVerbs() : Flowable<List<Verb>>

    @Query("SELECT * from  verb where id IN (:uid)")
    fun getVerbById(uid: Int) : Maybe<Verb>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateVerb(verb: Verb) : Single<Int>

    @Delete
    fun deleteVerb(verb: Verb) : Single<Int>

    @Transaction
    @Query("DELETE from verb")
    fun deleteAllVerbs() : Single<Int>

}
