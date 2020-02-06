package com.teamzmron.selfstudyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.teamzmron.selfstudyapp.Fragments.Home
import com.teamzmron.selfstudyapp.ViewModel.PageViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var pageViewModel : PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        pageViewModel.setFragment(Home())


        if(savedInstanceState == null) {
            pageViewModel.getFragmentTransaction(this)
                .replace(R.id.fragment_container, pageViewModel.getFragment().value!!).commit()
        }
    }
}
