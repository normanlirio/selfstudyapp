package com.teamzmron.selfstudyapp.ViewModel

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {
    var mutableFragment : MutableLiveData<Fragment> = MutableLiveData()
    var mutableBundle : MutableLiveData<Bundle> = MutableLiveData()

    fun setBundle(bundle: Bundle) {
        mutableBundle.value = bundle
    }

    fun getBundle() : LiveData<Bundle> {
        return mutableBundle
    }

    fun setFragment(fragment: Fragment) {
        mutableFragment.value = fragment
    }

    fun getFragment() : LiveData<Fragment> {
        return mutableFragment
    }

    fun getFragmentTransaction( context: Context) : FragmentTransaction {
        return (context as AppCompatActivity).supportFragmentManager.beginTransaction()
    }



}