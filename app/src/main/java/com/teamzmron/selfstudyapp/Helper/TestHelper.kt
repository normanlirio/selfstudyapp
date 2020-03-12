package com.teamzmron.selfstudyapp.Helper

import com.teamzmron.selfstudyapp.Room.Entity.Noun
class TestHelper  {
    companion object {
        fun insertNewWord() : Noun {
            return Noun(98, "a", "c", "a", "a", "a", "0101")
        }

        fun updateDeleteWord() : Noun {
            return Noun(98, "x", "2", "1", "3", "4", "0101")
        }
    }
}
