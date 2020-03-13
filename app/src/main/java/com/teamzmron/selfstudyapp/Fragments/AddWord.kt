package com.teamzmron.selfstudyapp.Fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import com.teamzmron.selfstudyapp.ViewModel.PageViewModel
import kotlinx.android.synthetic.main.fragment_add_word.*
import kotlinx.android.synthetic.main.layout_noun.*
import java.sql.Timestamp
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddWord : Fragment() {

    private lateinit var nounViewModel : NounViewModel
    private lateinit var pageViewModel : PageViewModel
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
        if(isNoun) {
            saveInputFromNounLayout()
            isNoun = true
        }
    }

    private fun initViewModels() {
        nounViewModel = ViewModelProvider(this).get(NounViewModel::class.java)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
    }



    private fun initRadioGroup() {
        radioGroup_addword.setOnCheckedChangeListener { _, checkedId ->
            run {
                when (checkedId) {
                    R.id.noun -> {
                        saveInputFromNounLayout()
                        setLayoutVisibility("noun")
                        isNoun = true
                    }
                    R.id.verb -> {
                        setLayoutVisibility("verb")
                        isNoun = false
                    }
                    R.id.adj -> {
                        setLayoutVisibility("adj")
                        isNoun = false
                    }
                }
            }
        }
    }

    private fun saveInputFromNounLayout() {
        button_addword_save.setOnClickListener {
            val noun = Noun(
                japanese = editText_noun_japanese.text.toString(),
                english = editText_noun_english.text.toString(),
                hiragana = editText_noun_hiragana.text.toString(),
                kanji = editText_noun_kanji.text.toString(),
                timestamp = getTimeStamp()
            )
            nounViewModel.saveToDB(noun).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if(it > 0) {
                    clearTextFields()
                    Toast.makeText(context!!, "Successfully added!", Toast.LENGTH_LONG).show()
                }
            })

        }

        button_addword_cancel.setOnClickListener {
            pageViewModel.getFragmentTransaction(context!!)
                .replace(pageViewModel.getContainer(), Home())
                .commit()
        }
    }

    private fun clearTextFields() {
        editText_noun_japanese.setText("")
        editText_noun_english.setText("")
        editText_noun_hiragana.setText("")
        editText_noun_kanji.setText("")
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
