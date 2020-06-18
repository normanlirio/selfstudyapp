package com.teamzmron.selfstudyapp.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
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

) : BaseWord(japaneseWord, englishWord, hiraganaForm, kanjiForm, currentTimestamp) {

    @Ignore
    constructor(adj: Adjective) : this(
        adjId = adj.adjId,
        adjType = adj.adjType,
        adjNegative = adj.adjNegative,
        adjPast = adj.adjPast,
        adjPastNegative = adj.adjPastNegative,
        englishWord = adj.englishWord,
        japaneseWord = adj.japaneseWord,
        hiraganaForm = adj.hiraganaForm,
        kanjiForm = adj.kanjiForm,
        currentTimestamp = adj.currentTimestamp
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Adjective

        return adjId == other.adjId && englishWord == other.englishWord && japaneseWord == other.japaneseWord && hiraganaForm == other.hiraganaForm && kanjiForm == other.kanjiForm
                && adjPast == other.adjPast && adjPast == other.adjPast && adjPastNegative == other.adjPastNegative && adjNegative == other.adjNegative && adjType == other.adjType
    }

}
