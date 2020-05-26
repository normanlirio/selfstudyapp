package com.teamzmron.selfstudyapp.Helper

import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.Room.Entity.Verb

class TestHelper  {
    companion object {
        fun insertNewWord() : Noun {
            return Noun(98, "a", "c", "a", "a",  "0101")
        }

        fun updateDeleteWord() : Noun {
            return Noun(98, "x", "2", "1", "3", "0101")
        }

        fun insertInitialNoun() : Noun {
            return Noun(0, "watashi", "i", "わたし", "", Utils.getTimeStamp() )
        }

        fun insertInitialVerb() : Verb {
            return Verb(0, "ru", "たべます", "たべました", "たべません", "たべませんでした", "eat",
            "taberu", "たべる", "食べる", Utils.getTimeStamp()
                )
        }

        fun insertInitiaAdjective() : Adjective {
            return Adjective(0, "na", "すきじゃない", "すきでした", "すきじゃなかった", "like", "sukina",
            "すき", "好き", Utils.getTimeStamp())
        }
    }
}
