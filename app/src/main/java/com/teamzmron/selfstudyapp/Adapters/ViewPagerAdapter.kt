package com.teamzmron.selfstudyapp.Adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var fragmentList : ArrayList<Fragment> = ArrayList()
    private var titleList : ArrayList<String> = ArrayList(

    )
    override fun getItem(position: Int): Fragment {
        Log.v("Tab", "Tab position: " + position)
      return fragmentList[position]
    }

    override fun getCount(): Int {
       return fragmentList.size
    }


    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}