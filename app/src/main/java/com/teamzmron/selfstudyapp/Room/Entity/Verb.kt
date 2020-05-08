package com.teamzmron.selfstudyapp.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "verb")
data class Verb (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var verbId:Int ? = null,

    @ColumnInfo(name = "type")
    var verbType:String ? = null,

    @ColumnInfo(name = "masu")
    var masu:String ? = null,

    @ColumnInfo(name = "masupast")
    var masuPast:String ? = null,

    @ColumnInfo(name = "masunegative")
    var masuNegative:String ? = null,

    @ColumnInfo(name = "masupastnegative")
    var masuPastNegative:String ? = null,

    var englishWord : String = "",
    var japaneseWord : String = "",
    var hiraganaForm: String = "",
    var kanjiForm: String = "",
    var currentTimestamp : String = ""

) : BaseWord( japaneseWord, englishWord, hiraganaForm, kanjiForm, currentTimestamp)