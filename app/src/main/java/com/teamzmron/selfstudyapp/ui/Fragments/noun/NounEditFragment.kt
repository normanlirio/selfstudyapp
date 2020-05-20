package com.teamzmron.selfstudyapp.ui.Fragments.noun

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.Helper.Utils.Companion.hideKeyboard
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ViewModel.SharedViewModel
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.Resource
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
        buttonActions()

    }

    private fun subscribeObservers() {
        sharedViewModel.mutableNoun.removeObservers(viewLifecycleOwner)
        sharedViewModel.mutableNoun.observe(viewLifecycleOwner, Observer {
            editText_noun_edit_id.setText(it.id!!.toString())
            editText_noun_edit_japanese.setText(it.japanese)
            editText_noun_edit_english.setText(it.english)
            editText_noun_edit_hiragana.setText(it.hiragana)
            editText_noun_edit_kanji.setText(it.kanji)

        })
    }

    private fun buttonActions() {

        button_noun_edit_save.setOnClickListener {
            updateNoun()
            it.hideKeyboard()
        }


        button_noun_edit_cancel.setOnClickListener {
            Utils.navigateToOtherFragment(requireActivity(), R.id.homeFragment)
            it.hideKeyboard()
        }
    }

    private fun updateNoun() {
        val noun = Noun(
            id = editText_noun_edit_id.text.toString().toInt(),
            japanese = editText_noun_edit_japanese.text.toString(),
            english =  editText_noun_edit_english.text.toString(),
            hiragana = editText_noun_edit_hiragana.text.toString(),
            kanji = editText_noun_edit_kanji.text.toString()
        )
        subscribeUpdate(noun)
    }

    private fun subscribeUpdate(noun: Noun) {
        nounViewModel.updateNoun(noun)
        nounViewModel.observeUpdateResult().removeObservers(viewLifecycleOwner)
        nounViewModel.observeUpdateResult().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v(TAG, "subscribeUpdate Update: Loading..")
                    }
                    Resource.Status.SUCCESS -> {

                        Log.v(TAG, "subscribeUpdate Update: Success..")
                        Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_SHORT).show()
                        Utils.navigateToOtherFragment(requireActivity(), R.id.homeFragment)

                    }
                    Resource.Status.ERROR -> {
                        Log.v(
                            TAG,"subscribeUpdate Update: Oops something went wrong. ${it.message}"
                        )
                    }
                }
            }
        })
    }



}
