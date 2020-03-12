package com.teamzmron.selfstudyapp.Room.DAO

import androidx.room.Dao
import androidx.room.Insert
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import io.reactivex.Single

@Dao
interface AdjectiveDAO {
    @Insert
    fun inserAdjective(adj: Adjective): Single<Long>
}