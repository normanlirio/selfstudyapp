package com.teamzmron.selfstudyapp.Room.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WordDAO: NounDAO, VerbDAO, AdjectiveDAO{

}