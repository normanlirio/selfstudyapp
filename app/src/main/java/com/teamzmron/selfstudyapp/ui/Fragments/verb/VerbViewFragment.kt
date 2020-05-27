package com.teamzmron.selfstudyapp.ui.Fragments.verb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.Observer
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_edit_adjective.*
import kotlinx.android.synthetic.main.fragment_edit_verb.*
import kotlinx.android.synthetic.main.fragment_view_adjective.*
import kotlinx.android.synthetic.main.fragment_view_verb.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class VerbViewFragment : BaseFragment() {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_verb, container, false)
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
            textView_verb_type.text = it.verbType
            textView_verb_masu.text = it.masu
            textView_verb_masupast.text = it.masuPast
            textView_verb_masunegative.text = it.masuNegative
            textView_verb_masupastnegative.text = it.masuPastNegative
            textView_verb_english.text = it.englishWord
            textView_verb_japanese.text = it.japaneseWord
            textView_verb_kanji.text = it.kanjiForm
            textView_verb_hiraganakatakana.text = it.hiraganaForm
            textView_verb_id.text = it.verbId.toString()

        })
    }


    private fun buttonActions() {
        button_verb_view.setOnClickListener {
            val verb = Verb(
                verbId = textView_verb_id.text.toString().toInt(),
                verbType = textView_verb_type.text.toString(),
                masu = textView_verb_masu.text.toString(),
                masuPast = textView_verb_masupast.text.toString(),
                masuNegative = textView_verb_masunegative.text.toString(),
                masuPastNegative = textView_verb_masupastnegative.text.toString(),
                englishWord = textView_verb_english.text.toString(),
                japaneseWord = textView_verb_japanese.text.toString(),
                hiraganaForm = textView_verb_hiraganakatakana.text.toString(),
                kanjiForm = textView_verb_kanji.text.toString()

            )
            sharedViewModel.setMutableVerb(verb)
            Utils.navigateToOtherFragment(requireActivity(), R.id.verbEdit)
        }
    }


}