package com.teamzmron.selfstudyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.teamzmron.selfstudyapp.Fragments.Home
import com.teamzmron.selfstudyapp.ViewModel.PageViewModel
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

    private lateinit var pageViewModel : PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)


        if(savedInstanceState == null) {
            pageViewModel.getFragmentTransaction(this)
                .replace(R.id.fragment_container, Home()).commit()
        }
    }
}
