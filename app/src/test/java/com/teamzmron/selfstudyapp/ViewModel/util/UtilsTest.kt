package com.teamzmron.selfstudyapp.ViewModel.util

import com.teamzmron.selfstudyapp.Helper.Constants
import com.teamzmron.selfstudyapp.Helper.Utils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UtilsTest {

    @ParameterizedTest
    @ValueSource(strings = ["yyyy-MM-dd HH:mm:ss"])
    fun test_isDateFormatCorrect(format: String) {
        print(Utils.getTimeStamp())
          assertTrue(isValidFormat(format, Utils.getTimeStamp()))
    }


    private fun isValidFormat(format: String?, value: String): Boolean {
        var date: Date? = null
        try {
            val sdf = SimpleDateFormat(format)
            date = sdf.parse(value)
            if (value != sdf.format(date)) {
                date = null
            }
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        return date != null
    }
}