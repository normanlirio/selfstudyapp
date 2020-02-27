package com.teamzmron.selfstudyapp.Helper

import com.teamzmron.selfstudyapp.Room.Entity.Word
class TestHelper  {
    companion object {
        fun insertNewWord() : Word {
            return Word(98, "a", "c", "a", "a", "a", "0101")
        }

        fun updateDeleteWord() : Word {
            return Word(98, "x", "2", "1", "3", "4", "0101")
        }
    }
}
