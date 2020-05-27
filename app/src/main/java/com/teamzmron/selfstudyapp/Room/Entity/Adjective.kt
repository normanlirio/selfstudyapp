package com.teamzmron.selfstudyapp.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adjective")
data class Adjective(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var adjId:Int ? = null,


    @ColumnInfo(name = "type")
    var adjType:String ? = null,

    @ColumnInfo(name = "negative")
    var adjNegative:String ? = null,

    @ColumnInfo(name = "past")
    var adjPast:String ? = null,

    @ColumnInfo(name = "pastnegative")
    var adjPastNegative:String ? = null,

    var englishWord : String = "",
    var japaneseWord : String = "",
    var hiraganaForm: String = "",
    var kanjiForm: String = "",
    var currentTimestamp : String = ""

) : BaseWord(japaneseWord, englishWord, hiraganaForm, kanjiForm, currentTimestamp)
