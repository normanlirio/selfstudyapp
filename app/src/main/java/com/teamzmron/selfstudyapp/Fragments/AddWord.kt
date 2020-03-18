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
        buttonActions(NOUN)
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
                        buttonActions(NOUN)
                        setLayoutVisibility(NOUN)
                        isNoun = true
                    }
                    R.id.verb -> {
                        buttonActions(VERB)
                        setLayoutVisibility(VERB)
                        isNoun = false
                    }
                    R.id.adj -> {
                        buttonActions(ADJ)
                        setLayoutVisibility(ADJ)
                        isNoun = false
                    }
                }
            }
        }
    }

    private fun buttonActions(category: String) {
        button_addword_save.setOnClickListener {
            when (category) {
                NOUN -> {
                    saveInputFromNounLayout()
                }
                VERB -> {
                    saveInputFromVerbLayout()
                }
                ADJ -> {
                    saveInputFromAdjectiveLayout()
                }
            }
        }


        button_addword_cancel.setOnClickListener {
            pageViewModel.getFragmentTransaction(context!!)
                .replace(pageViewModel.getContainer(), Home())
                .commit()
        }

    }

    private fun saveInputFromNounLayout() {
        val noun = Noun(
            japanese = editText_noun_japanese.text.toString(),
            english = editText_noun_english.text.toString(),
            hiragana = editText_noun_hiragana.text.toString(),
            kanji = editText_noun_kanji.text.toString(),
            timestamp = getTimeStamp()
        )
        nounViewModel.saveToDB(noun).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it > 0) {
                clearTextFields(NOUN)
                Toast.makeText(context!!, "Successfully added!", Toast.LENGTH_LONG).show()
            }
        })


    }

    private fun saveInputFromVerbLayout() {
        val verbType = if (getVerbType().contains("")) "u" else "ru"
        val verb = Verb(
            verbType = verbType,
            masu = editText_verb_masu.text.toString(),
            masuPast = editText_verb_masupast.text.toString(),
            masuNegative = editText_verb_masunegative.text.toString(),
            masuPastNegative = editText_verb_masupastnegative.text.toString(),
            englishWord = editText_verb_englishWord.text.toString(),
            japaneseWord = editText_verb_japaneseWord.text.toString(),
            hiraganaForm = editText_verb_hirakata.text.toString(),
            kanjiForm = editText_verb_kanji.text.toString(),
            currentTimestamp = getTimeStamp()
        )
        verbViewModel.saveToDB(verb).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it > 0) {
                clearTextFields(VERB)
                Toast.makeText(context!!, "Successfully added!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getVerbType(): String {
        return radioGroup_verb.findViewById<RadioButton>(radioGroup_verb.checkedRadioButtonId)
            .text.toString()
    }

    private fun saveInputFromAdjectiveLayout() {
        val adjType = if(getAdjType().contains("な")) "na" else "i"
        val adjective = Adjective(
            adjType = adjType,
            adjNegative = editText_adj_negative.text.toString(),
            adjPast = editText_adj_past.text.toString(),
            adjPastNegative = editText_adj_pastnegative.text.toString(),
            englishWord = editText_adj_englishWord.text.toString(),
            japaneseWord = editText_adj_japaneseWord.text.toString(),
            hiraganaForm = editText_adj_hirakata.text.toString(),
            kanjiForm = editText_adj_kanji.text.toString(),
            currentTimestamp = getTimeStamp()
        )

        adjectiveViewModel.saveToDB(adjective).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it > 0) {
                clearTextFields(ADJ)
                Toast.makeText(context!!, "Successfully added!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getAdjType() : String {
        return radioGroup_adj.findViewById<RadioButton>(radioGroup_adj.checkedRadioButtonId).text.toString()
    }



    private fun clearTextFields(category: String) {
        when (category) {
            NOUN -> {
                editText_noun_japanese.text.clear()
                editText_noun_english.text.clear()
                editText_noun_hiragana.text.clear()
                editText_noun_kanji.text.clear()
            }
            VERB -> {
                editText_verb_masu.text.clear()
                editText_verb_masupast.text.clear()
                editText_verb_masunegative.text.clear()
                editText_verb_masupastnegative.text.clear()
                editText_verb_englishWord.text.clear()
                editText_verb_japaneseWord.text.clear()
                editText_verb_hirakata.text.clear()
                editText_verb_kanji.text.clear()
            }
            ADJ -> {
                editText_adj_negative.text.clear()
                editText_adj_past.text.clear()
                editText_adj_pastnegative.text.clear()
                editText_adj_englishWord.text.clear()
                editText_adj_japaneseWord.text.clear()
                editText_adj_hirakata.text.clear()
                editText_adj_kanji.text.clear()
            }
        }
    }


    private fun getTimeStamp(): String {
        val date = Date()
        val time: Long = date.time
        val ts = Timestamp(time)
        return ts.toString()
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
