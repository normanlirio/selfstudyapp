package com.teamzmron.selfstudyapp.Fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.ViewModel.WordViewModel
import kotlinx.android.synthetic.main.fragment_add_word.*

/**
 * A simple [Fragment] subclass.
 */
class AddWord : Fragment() {

    private lateinit var wordViewModel : WordViewModel

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

    }

    private fun initViewModels() {
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
    }

    private fun initRadioGroup() {
        radioGroup_addword.setOnCheckedChangeListener { group, checkedId ->
            run {
                when (checkedId) {
                    R.id.noun -> {

                        setLayoutVisibility("noun")
                    }
                    R.id.verb -> {

                        setLayoutVisibility("verb")
                    }
                    R.id.adj -> {

                        setLayoutVisibility("adj")
                    }
                }
            }
        }
    }

    private fun setLayoutVisibility(category: String) {
        var nounVisibility = View.GONE
        var verbVisibility = View.GONE
        var adjVisibility = View.GONE
        if (category == "noun") {
            Log.v("RG", "NOUN")
            nounVisibility = View.VISIBLE
            adjVisibility = View.GONE
            verbVisibility = View.GONE
        }
        if (category == "verb") {
            Log.v("RG", "VERB")
            verbVisibility = View.VISIBLE
            nounVisibility = View.GONE
            adjVisibility = View.GONE
        }
        if (category == "adj") {
            Log.v("RG", "ADJ")
            adjVisibility = View.VISIBLE
            nounVisibility = View.GONE
            verbVisibility = View.GONE
        }


        linearlayout_addword_nounLayout.visibility = nounVisibility
        linearlayout_addword_adjLayout.visibility = adjVisibility
        linearlayout_addword_verbLayout.visibility = verbVisibility
    }


}
