package com.teamzmron.selfstudyapp.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.ViewModel.AdjectiveViewModel
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import com.teamzmron.selfstudyapp.ViewModel.VerbViewModel
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment : DaggerFragment() {

     lateinit var nounViewModel: NounViewModel
     lateinit var adjectiveViewModel: AdjectiveViewModel
     lateinit var verbViewModel: VerbViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nounViewModel = ViewModelProvider(this, viewModelProviderFactory).get(NounViewModel::class.java)
        verbViewModel = ViewModelProvider(this, viewModelProviderFactory).get(VerbViewModel::class.java)
        adjectiveViewModel = ViewModelProvider(this, viewModelProviderFactory).get(AdjectiveViewModel::class.java)

    }
}