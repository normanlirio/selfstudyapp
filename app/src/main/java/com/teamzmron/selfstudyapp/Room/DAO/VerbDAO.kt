package com.teamzmron.selfstudyapp.Room.DAO

import androidx.room.Dao
import androidx.room.Insert
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import io.reactivex.Single

@Dao
interface VerbDAO  {

    @Insert
    fun insertVerb(verb: Verb): Single<Long>

}
