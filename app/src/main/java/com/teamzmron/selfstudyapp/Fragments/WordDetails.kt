package com.teamzmron.selfstudyapp.Fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.teamzmron.selfstudyapp.Adapters.WordsAdapter

import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Word
import com.teamzmron.selfstudyapp.ViewModel.PageViewModel
import com.teamzmron.selfstudyapp.ViewModel.WordDetailsViewModel
import com.teamzmron.selfstudyapp.ViewModel.WordViewModel
import kotlinx.android.synthetic.main.fragment_worddetails.*

/**
 * A simple [Fragment] subclass.
 */
class WordDetails : Fragment() {
    private lateinit var wordViewModel : WordViewModel
    private lateinit var pageViewModel: PageViewModel
    private lateinit var wordDetailsViewModel: WordDetailsViewModel
    private var wordID : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_worddetails, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModels()

        wordID = arguments!!.getInt("id")
        wordDetailsViewModel.getMutableId().observe(this, Observer {

            Log.v("Observer", "Word ID: " + it)
        })


        wordViewModel.getWordById(wordID).observe(this, Observer {
            editText_editVocabulary_addword.setText(it[0].japanese)
            editText_editVocabulary_addEnglish.setText(it[0].english)
            editText_editVocabulary_hiragana.setText(it[0].hiragana)
            editText_editVocabulary_kanji.setText(it[0].kanji)
            editText_editVocabulary_sentence.setText(it[0].sentence)
        })

        buttonActions()

    }

    private fun initViewModels() {
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        wordDetailsViewModel = ViewModelProviders.of(activity!!).get(WordDetailsViewModel::class.java)


    }

    private fun buttonActions() {

        button_editVocabulary_save.setOnClickListener {

          goHome()
            wordViewModel.updateWord(Word(
                id = wordID,
                japanese = editText_editVocabulary_addword.text.toString(),
                english = editText_editVocabulary_addEnglish.text.toString(),
                hiragana = editText_editVocabulary_hiragana.text.toString(),
                kanji = editText_editVocabulary_kanji.text.toString(),
                sentence = editText_editVocabulary_sentence.text.toString()
                ))


        }

        button_editVocabulary_cancel.setOnClickListener {
            goHome()
        }
    }

    private fun goHome() {
        pageViewModel.getFragmentTransaction(context!!).replace(pageViewModel.fragmentContainer, Home()).commit()
    }

}
