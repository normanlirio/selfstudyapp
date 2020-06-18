package com.teamzmron.selfstudyapp.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "noun")
class Noun(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int ? = null,

    japanese: String? = null,
    english: String? = null,
    hiragana: String?= null,
    kanji: String?= null,
    timestamp: String? = null
) : BaseWord(japanese, english, hiragana, kanji, timestamp) {

    @Ignore
    constructor(noun: Noun) : this(
        english = noun.english,
        japanese = noun.japanese,
        hiragana = noun.hiragana,
        kanji = noun.kanji,
        timestamp = noun.timestamp

    )


}