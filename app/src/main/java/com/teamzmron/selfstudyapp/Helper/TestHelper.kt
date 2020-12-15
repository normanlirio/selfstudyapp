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

        fun insertInitialNoun() : List<Noun> {
            val list = ArrayList<Noun>()
            list.apply {
                add(
                    Noun(0, "watashi", "i", "わたし", "私", Utils.getTimeStamp() )
                )
                add(
                    Noun(1, "gyuunyuu", "milk", "わたし", "牛乳", Utils.getTimeStamp() )
                )
                add(
                    Noun(2, "natsu", "summer", "なつ", "夏", Utils.getTimeStamp() )
                )
                add(
                    Noun(3, "machi", "city", "まち", "町", Utils.getTimeStamp() )
                )
                add(
                    Noun(4, "hon", "book", "ほん", "本", Utils.getTimeStamp() )
                )
            }
            return list
        }

        fun insertInitialVerb() : List<Verb> {
            val list = ArrayList<Verb>()
            list.apply {
                add(
                    Verb(0, "ru", "たべます", "たべました", "たべません", "たべませんでした", "to eat",
                        "taberu", "たべる", "食べる", Utils.getTimeStamp()
                    )

                )

                add(
                    Verb(1, "u", "とびます", "とびました", "とびません", "とべませんでした", "to fly",
                        "tobu", "とぶ", "飛ぶ", Utils.getTimeStamp()
                    )
                )

                add(
                    Verb(2, "u", "かえります", "かえりました", "かえりません", "かえりませんでした", "to return home",
                        "kaeru", "かえる", "帰る", Utils.getTimeStamp()
                    )
                )

                add(
                    Verb(3, "u", "かいます", "かいました", "かいません", "かいませんでした", "to buy",
                        "kau", "かう", "買う", Utils.getTimeStamp()
                    )
                )

                add(
                    Verb(4, "ru", "こたえます", "こたえました", "こたえません", "こたえませんでした", "to answer",
                        "kotaeru", "こたえる", "答える", Utils.getTimeStamp()
                    )
                )
            }


            return list
        }

        fun insertInitiaAdjective() : List<Adjective> {
            val list = ArrayList<Adjective>()
            list.apply {
                add(
                    Adjective(0, "na", "すきじゃない", "すきでした", "すきじゃなかった", "like", "sukina",
                        "すき", "好き", Utils.getTimeStamp())
                )
                add(
                    Adjective(1, "i", "たかくない", "たかかった", "たかくなかった", "high/expensive", "takai",
                        "たかい", "⾼鱉", Utils.getTimeStamp())
                )
                add(
                    Adjective(2, "na", "しずかじゃない", "しずかでした", "しずかじゃなかった", "Quiet", "Shizukana",
                        "しずかな", "静鱐鱯", Utils.getTimeStamp())
                )
                add(
                    Adjective(3, "i", "おいしくない", "おいしくなかった", "おいしくなかった", "Delicious; tasty", "Oishii",
                        "おいしい", "美味鱜鱉", Utils.getTimeStamp())
                )
                add(
                    Adjective(4, "na", "べんりじゃない", "べんりでした", "べんりじゃなかった", "Convenient", "Benrina",
                        "べんりな", "便利鱯", Utils.getTimeStamp())
                )
            }
            return list
        }
    }
}
