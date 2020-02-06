package com.teamzmron.selfstudyapp.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int ? = null,

    @ColumnInfo(name = "japanese")
    var japanese:String ? = null,

    @ColumnInfo(name = "english")
    var english:String ? = null,

    @ColumnInfo(name = "hiragana")
    var hiragana:String ? = null,

    @ColumnInfo(name = "kanji")
    var kanji:String ? = null,

    @ColumnInfo(name = "sentence")
    var sentence:String ? = null,

    @ColumnInfo(name = "timestamp")
    var timestamp: String ? = null
)