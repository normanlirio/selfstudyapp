package com.teamzmron.selfstudyapp.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noun")
class Noun(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int ? = null,

    japanese: String = "",
    english: String= "",
    hiragana: String= "",
    kanji: String= "",
    timestamp: String= ""
) : BaseWord(japanese, english, hiragana, kanji, timestamp)