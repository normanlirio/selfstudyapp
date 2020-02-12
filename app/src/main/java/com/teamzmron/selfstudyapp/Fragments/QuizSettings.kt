package com.teamzmron.selfstudyapp.Fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.ViewModel.QuizSettingsViewModel

class QuizSettings : Fragment() {

    companion object {
        fun newInstance() = QuizSettings()
    }

    private lateinit var viewModel: QuizSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quiz_settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuizSettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
