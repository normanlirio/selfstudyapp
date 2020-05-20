package com.teamzmron.selfstudyapp.ui.Fragments.noun

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_edit_noun.*
import kotlinx.android.synthetic.main.fragment_view_noun.*
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class NounViewFragment : BaseFragment() {

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
        return inflater.inflate(R.layout.fragment_view_noun, container, false)
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
            textView_noun_id.setText(it.id!!.toString())
            textView_noun_japanese.setText(it.japanese)
            textView_noun_english.setText(it.english)
            textView_noun_hiraganakatakana.setText(it.hiragana)
            textView_noun_kanji.setText(it.kanji)

        })
    }


    private fun buttonActions() {
      button_noun_view.setOnClickListener {
          val noun = Noun(
              id = textView_noun_id.text.toString().toInt(),
              japanese = textView_noun_japanese.text.toString(),
              english =  textView_noun_english.text.toString(),
              hiragana = textView_noun_hiraganakatakana.text.toString(),
              kanji = textView_noun_kanji.text.toString()
          )
          sharedViewModel.setMutableNoun(noun)
          Utils.navigateToOtherFragment(requireActivity(), R.id.nounEdit)
      }
    }
}