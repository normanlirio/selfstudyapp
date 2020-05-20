package com.teamzmron.selfstudyapp.Helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.teamzmron.selfstudyapp.R
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

        fun navigateToOtherFragment(activity: Activity, fragment: Int) {
            Navigation.findNavController(activity,
                R.id.nav_host_fragment
            )
                .navigate(fragment, null, NavOptions.Builder().setPopUpTo(R.id.homeFragment, false).build())
        }

        fun View.hideKeyboard() {
            val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}