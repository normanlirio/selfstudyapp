package com.teamzmron.selfstudyapp

import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import org.mockito.Mockito
import java.util.*

class TestUtil {

    companion object {
        val NOUN = Noun()
        val VERB = Verb()
        val ADJ = Adjective()

        val TEST_NOUN = Noun(japanese = "watashi", english =  "i", hiragana = "わたし", kanji = "私", timestamp = "06-2020")
        val TEST_VERB = Verb(verbType = "ru", masu =  "たべます",masuPast =  "たべました", masuNegative = "たべません", masuPastNegative = "たべませんでした",englishWord =  "eat",
           japaneseWord =  "taberu",hiraganaForm =  "たべる", kanjiForm = "食べる", currentTimestamp = "06-2020")
        val TEST_ADJ = Adjective(adjType = "na", adjNegative = "すきじゃない",adjPast =  "すきでした", adjPastNegative = "すきじゃなかった", englishWord = "like",japaneseWord =  "sukina",
          hiraganaForm =   "すき",kanjiForm =  "好き", currentTimestamp = "06-2020")


        val TEST_NOUN_LIST: List<Noun> = Collections.unmodifiableList(
            object : ArrayList<Noun>() {
                init {
                    add(Noun(japanese = "watashi", english =  "i", hiragana = "わたし", kanji = "私", timestamp = "06-2020"))
                    add(Noun(japanese = "hikouki", english =  "plane", hiragana = "ひこうき", kanji = "飛行機", timestamp = "06-2020"))
                }
            }
        )

        val TEST_VERB_LIST: List<Verb> = Collections.unmodifiableList(
            object : ArrayList<Verb>() {
                init {
                    add(Verb(verbType = "ru", masu =  "たべます",masuPast =  "たべました", masuNegative = "たべません", masuPastNegative = "たべませんでした",englishWord =  "eat",
                        japaneseWord =  "taberu",hiraganaForm =  "たべる", kanjiForm = "食べる", currentTimestamp = "06-2020"))
                    add(Verb(verbType = "u", masu =  "のむます",masuPast =  "のみました", masuNegative = "のみません", masuPastNegative = "のみませんでした",englishWord =  "drink",
                        japaneseWord =  "nomu",hiraganaForm =  "のむ", kanjiForm = "飲む", currentTimestamp = "06-2020"))
                }
            }
        )

        val TEST_ADJ_LIST: List<Adjective> = Collections.unmodifiableList(
            object : ArrayList<Adjective>() {
                init {
                    add(Adjective(adjType = "na", adjNegative = "すきじゃない",adjPast =  "すきでした", adjPastNegative = "すきじゃなかった", englishWord = "like",japaneseWord =  "sukina",
                        hiraganaForm =   "すき",kanjiForm =  "好き", currentTimestamp = "06-2020"))
                    add(Adjective(adjType = "i", adjNegative = "おいしくない",adjPast =  "おいしかった", adjPastNegative = "おいしくなかった", englishWord = "tasty",japaneseWord =  "oishii",
                        hiraganaForm =   "おいしい",kanjiForm =  "美味しい", currentTimestamp = "06-2020"))
                }
            }
        )


        fun <T> any(type : Class<T>): T {
            Mockito.any(type)
            return uninitialized()
        }

        private fun <T> uninitialized(): T = null as T
    }


}