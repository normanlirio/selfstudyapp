package com.teamzmron.selfstudyapp.ui.Fragments.Adjective

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.Observer
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_edit_adjective.*
import kotlinx.android.synthetic.main.fragment_view_adjective.*
import kotlinx.android.synthetic.main.fragment_view_noun.*
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class AdjectiveViewFragment : BaseFragment() {

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
        return inflater.inflate(R.layout.fragment_view_adjective, container, false)
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
           textView_adjective_type.text = it.adjType
            textView_adjective_english.text = it.englishWord
            textView_adjective_japanese.text = it.japaneseWord
            textView_adjective_hiraganakatakana.text = it.hiraganaForm
            textView_adjective_kanji.text = it.kanjiForm
            textView_adjective_negative.text = it.adjNegative
            textView_adjective_pastnegative.text = it.adjPastNegative
            textView_adjective_past.text = it.adjPast
            textView_adjective_id.text = it.adjId.toString()

        })
    }

    private fun buttonActions() {
        button_adjective_view.setOnClickListener {
            val adjective = Adjective(
                adjId = textView_adjective_id.text.toString().toInt(),
                adjType = textView_adjective_type.text.toString(),
                adjNegative = textView_adjective_negative.text.toString(),
                adjPast = textView_adjective_past.text.toString(),
                adjPastNegative = textView_adjective_pastnegative.text.toString(),
                englishWord = textView_adjective_english.text.toString(),
                japaneseWord = textView_adjective_japanese.text.toString(),
                hiraganaForm = textView_adjective_hiraganakatakana.text.toString(),
                kanjiForm = textView_adjective_kanji.text.toString()
            )
            sharedViewModel.setMutableAdjective(adjective)
            Utils.navigateToOtherFragment(requireActivity(), R.id.adjEdit)
        }
    }


}