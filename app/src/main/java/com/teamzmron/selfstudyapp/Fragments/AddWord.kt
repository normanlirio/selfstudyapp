package com.teamzmron.selfstudyapp.Fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ViewModel.AdjectiveViewModel
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import com.teamzmron.selfstudyapp.ViewModel.PageViewModel
import com.teamzmron.selfstudyapp.ViewModel.VerbViewModel
import kotlinx.android.synthetic.main.fragment_add_word.*
import kotlinx.android.synthetic.main.layout_adjective.*
import kotlinx.android.synthetic.main.layout_noun.*
import kotlinx.android.synthetic.main.layout_verb.*
import java.sql.Timestamp
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddWord : Fragment() {

    private lateinit var nounViewModel: NounViewModel
    private lateinit var pageViewModel: PageViewModel
    private lateinit var adjectiveViewModel: AdjectiveViewModel
    private lateinit var verbViewModel: VerbViewModel

    private val NOUN = "noun"
    private val VERB = "verb"
    private val ADJ = "adj"
    private var isNoun = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_word, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModels()
        initRadioGroup()
        setLayoutVisibility(NOUN)
    }

    private fun initViewModels() {
        nounViewModel = ViewModelProvider(this).get(NounViewModel::class.java)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        adjectiveViewModel = ViewModelProvider(this).get(AdjectiveViewModel::class.java)
        verbViewModel = ViewModelProvider(this).get(VerbViewModel::class.java)
    }


    private fun initRadioGroup() {

        radioGroup_addword.setOnCheckedChangeListener { _, checkedId ->
            run {
                when (checkedId) {
                    R.id.noun -> {
                        setLayoutVisibility(NOUN)
                        isNoun = true
                    }
                    R.id.verb -> {
                        setLayoutVisibility(VERB)
                        isNoun = false
                    }
                    R.id.adj -> {
                        setLayoutVisibility(ADJ)
                        isNoun = false
                    }
                }
            }
        }
    }



    private fun setLayoutVisibility(category: String) {

        var container = R.id.layout_fragmentcontainer
        if (category == NOUN) {
            Log.v("RG", "NOUN")
            pageViewModel.getFragmentTransaction(context!!)
                .replace(container, NounFragment())
                .commit()
        }
        if (category == VERB) {
            Log.v("RG", "VERB")
            pageViewModel.getFragmentTransaction(context!!)
                .replace(container, VerbFragment())
                .commit()
        }
        if (category == ADJ) {
            Log.v("RG", "ADJ")
            pageViewModel.getFragmentTransaction(context!!)
                .replace(container, AdjectiveFragment())
                .commit()
        }


    }


}
