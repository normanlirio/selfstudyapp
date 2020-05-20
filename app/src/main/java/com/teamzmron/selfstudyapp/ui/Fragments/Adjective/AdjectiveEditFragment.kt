package com.teamzmron.selfstudyapp.ui.Fragments.Adjective

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.Resource
import com.teamzmron.selfstudyapp.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_add_adjective.*
import kotlinx.android.synthetic.main.fragment_edit_adjective.*
import kotlinx.android.synthetic.main.fragment_edit_noun.*
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdjectiveEditFragment : BaseFragment() {
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
        return inflater.inflate(R.layout.fragment_edit_adjective, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeActivity.lockDrawer()
        subscribeObservers()
        buttonActions()
    }

    private fun subscribeObservers() {
        sharedViewModel.mutableAdjective.removeObservers(viewLifecycleOwner)
        sharedViewModel.mutableAdjective.observe(viewLifecycleOwner, Observer {
            if (it.adjType.equals("i")) {
                radio_i_edit.isChecked = true
            } else {
                radio_na_edit.isChecked = true
            }
            editText_adj_edit_englishWord.setText(it.englishWord)
            editText_adj_edit_japaneseWord.setText(it.japaneseWord)
            editText_adj_edit_hirakata.setText(it.hiraganaForm)
            editText_adj_edit_kanji.setText(it.kanjiForm)
            editText_adj_edit_negative.setText(it.adjNegative)
            editText_adj_edit_pastnegative.setText(it.adjPastNegative)
            editText_adj_edit_past.setText(it.adjPast)
            editText_adj_edit_id.setText(it.adjId.toString())

        })
    }

    private fun buttonActions() {

        button_adj_edit_save.setOnClickListener {
            updateAdjective()
        }


        button_adj_edit_cancel.setOnClickListener {
            Utils.navigateToOtherFragment(requireActivity(), R.id.homeFragment)
        }
    }

    private fun updateAdjective() {
        val adjType = if (getAdjType().contains("な")) "na" else "i"
        val adj = Adjective(
            adjId = editText_adj_edit_id.text.toString().toInt(),
            adjType = adjType,
            adjNegative = editText_adj_edit_negative.text.toString(),
            adjPast = editText_adj_edit_past.text.toString(),
            adjPastNegative = editText_adj_edit_pastnegative.text.toString(),
            englishWord = editText_adj_edit_englishWord.text.toString(),
            japaneseWord = editText_adj_edit_japaneseWord.text.toString(),
            hiraganaForm = editText_adj_edit_hirakata.text.toString(),
            kanjiForm = editText_adj_edit_kanji.text.toString()
        )
        subscribeUpdateAdjective(adj)
    }

    private fun getAdjType(): String {
        return radioGroup_adj_edit.findViewById<RadioButton>(radioGroup_adj_edit.checkedRadioButtonId).text.toString()
    }


    private fun subscribeUpdateAdjective(adjective: Adjective) {
        adjectiveViewModel.updateAdjective(adjective)
        adjectiveViewModel.observeUpdateResult().removeObservers(viewLifecycleOwner)
        adjectiveViewModel.observeUpdateResult().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v(TAG, "subscribeUpdateAdjective Update: Loading..")
                    }
                    Resource.Status.SUCCESS -> {

                        Log.v(TAG, "subscribeUpdateAdjective Update: Success..")
                        Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_SHORT).show()
                        Utils.navigateToOtherFragment(requireActivity(), R.id.homeFragment)

                    }
                    Resource.Status.ERROR -> {
                        Log.v(
                            TAG,"subscribeUpdateAdjective Update: Oops something went wrong. ${it.message}"
                        )
                    }
                }
            }
        })
    }

}