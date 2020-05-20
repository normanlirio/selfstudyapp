package com.teamzmron.selfstudyapp.ui.Fragments.verb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.Helper.Utils.Companion.hideKeyboard
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.Resource
import com.teamzmron.selfstudyapp.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_add_verb.*
import kotlinx.android.synthetic.main.fragment_edit_adjective.*
import kotlinx.android.synthetic.main.fragment_edit_verb.*
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class VerbEditFragment  : BaseFragment() {
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
        return inflater.inflate(R.layout.fragment_edit_verb, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeActivity.lockDrawer()
        subscribeObservers()
        buttonActions()

    }


    private fun subscribeObservers() {
        sharedViewModel.mutableVerb.removeObservers(viewLifecycleOwner)
        sharedViewModel.mutableVerb.observe(viewLifecycleOwner, Observer {
            if (it.verbType.equals("u")) {
                radioButton_verb_edit_u.isChecked = true
            } else {
                radioButton_verb_edit_ru.isChecked = true
            }
            editText_verb_edit_masu.setText(it.masu)
            editText_verb_edit_masupast.setText(it.masuPast)
            editText_verb_edit_masunegative.setText(it.masuNegative)
            editText_verb_edit_masupastnegative.setText(it.masuPastNegative)
            editText_verb_edit_englishWord.setText(it.englishWord)
            editText_verb_edit_japaneseWord.setText(it.japaneseWord)
            editText_verb_edit_kanji.setText(it.kanjiForm)
            editText_verb_edit_hirakata.setText(it.hiraganaForm)
            editText_verb_edit_id.setText(it.verbId.toString())
        })
    }

    private fun buttonActions() {
        button_verb_edit_save.setOnClickListener {
          updateVerb()
            it.hideKeyboard()
        }

        button_verb_edit_cancel.setOnClickListener {
            Utils.navigateToOtherFragment(requireActivity(), R.id.homeFragment)
            it.hideKeyboard()
        }
    }

    private fun updateVerb() {
        val verbType = if (getVerbType().contains("う")) "u" else "ru"
        val verb = Verb(
            verbId = editText_verb_edit_id.text.toString().toInt(),
            verbType = verbType,
            masu = editText_verb_edit_masu.text.toString(),
            masuPast = editText_verb_edit_masupast.text.toString(),
            masuNegative = editText_verb_edit_masunegative.text.toString(),
            masuPastNegative = editText_verb_edit_masupastnegative.text.toString(),
            englishWord = editText_verb_edit_englishWord.text.toString(),
            japaneseWord = editText_verb_edit_japaneseWord.text.toString(),
            hiraganaForm = editText_verb_edit_hirakata.text.toString(),
            kanjiForm = editText_verb_edit_kanji.text.toString()

        )
        subscribeUpdateverb(verb)
    }

    private fun subscribeUpdateverb(verb: Verb) {
        verbViewModel.updateVerb(verb)
        verbViewModel.observeUpdateResult().removeObservers(viewLifecycleOwner)
        verbViewModel.observeUpdateResult().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v(TAG, "subscribeUpdateverb Update: Loading..")
                    }
                    Resource.Status.SUCCESS -> {

                        Log.v(TAG, "subscribeUpdateverb Update: Success..")
                        Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_SHORT).show()
                        Utils.navigateToOtherFragment(requireActivity(), R.id.homeFragment)

                    }
                    Resource.Status.ERROR -> {
                        Log.v(
                            TAG,"subscribeUpdateverb Update: Oops something went wrong. ${it.message}"
                        )
                    }
                }
            }
        })
    }

    private fun getVerbType(): String {
        return radioGroup_verb_edit.findViewById<RadioButton>(radioGroup_verb_edit.checkedRadioButtonId)
            .text.toString()
    }
}