package com.teamzmron.selfstudyapp.ui.Fragments.noun

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.ViewModel.SharedViewModel
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_edit_noun.*
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class NounEditFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = javaClass.simpleName



    @Inject
    lateinit var homeActivity: HomeActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v("NounHomeFragment", "onCreateView: set OnCreateView!")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_noun, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeActivity.lockDrawer()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        sharedViewModel.mutableNoun.removeObservers(viewLifecycleOwner)
        sharedViewModel.mutableNoun.observe(viewLifecycleOwner, Observer {
            editText_noun_edit_japanese.setText(it.japanese)
            editText_noun_edit_english.setText(it.english)
            editText_noun_edit_hiragana.setText(it.hiragana)
            editText_noun_edit_kanji.setText(it.kanji)

        })
    }
}
