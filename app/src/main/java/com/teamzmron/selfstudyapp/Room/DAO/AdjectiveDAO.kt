package com.teamzmron.selfstudyapp.Room.DAO

import androidx.room.*
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface AdjectiveDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAdjective(adjective : Adjective) : Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInitialAdjective(adjective : Adjective)

    @Query("SELECT * from adjective ORDER BY english ASC")
    fun getAdjectives() : Flowable<List<Adjective>>

    @Query("SELECT * from  adjective where id IN (:uid)")
    fun getAdjectiveById(uid: Int) : Maybe<Adjective>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAdjective(adjective: Adjective) : Single<Int>

    @Delete
    fun deleteAdjective(adjective: Adjective) : Single<Int>

    @Transaction
    @Query("DELETE from adjective")
    fun deleteAllAdjectives() : Single<Int>
}