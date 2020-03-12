package com.teamzmron.selfstudyapp.Room.Entity

import androidx.room.Entity

@Entity(tableName = "noun")
class Noun(
    id: Int,
    japanese: String,
    english: String,
    hiragana: String,
    kanji: String,
    timestamp: String
) : BaseWord(id, japanese, english, hiragana, kanji, timestamp)