package com.teamzmron.selfstudyapp.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
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

) : BaseWord( japaneseWord, englishWord, hiraganaForm, kanjiForm, currentTimestamp) {

    @Ignore
    constructor(verb: Verb) : this(verbId =verb.verbId,
        verbType = verb.verbType,
        masu =verb.masu,
        masuPast = verb.masuPast,
        masuNegative = verb.masuNegative,
        masuPastNegative = verb.masuPastNegative,
        englishWord = verb.englishWord,
        japaneseWord = verb.japaneseWord,
        hiraganaForm = verb.hiraganaForm,
        kanjiForm = verb.kanjiForm,
        currentTimestamp = verb.currentTimestamp)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Verb

        return verbId == other.verbId && englishWord == other.englishWord && japaneseWord == other.japaneseWord && hiraganaForm == other.hiraganaForm && kanjiForm == other.kanjiForm && masu == other.masu &&
                masuPast == other.masuPast && masuNegative == other.masuPast && masuPastNegative == other.masuPastNegative
    }

}