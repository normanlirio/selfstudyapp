package com.teamzmron.selfstudyapp.Helper

import java.sql.Timestamp
import java.util.*

class Utils {

    companion object{
         fun getTimeStamp(): String {
            val date = Date()
            val time: Long = date.time
            val ts = Timestamp(time)
            return ts.toString()
        }
    }
}