package com.teamzmron.selfstudyapp.ui.Fragments.verb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ViewModel.VerbViewModel
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_verb.*
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VerbAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VerbAddFragment : DaggerFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var verbViewModel: VerbViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

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
        return inflater.inflate(R.layout.fragment_add_verb, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        verbViewModel = ViewModelProvider(this, providerFactory).get(VerbViewModel::class.java)

        radioButton_verb_u.isChecked = true

        button_verb_save.setOnClickListener {
            if (checkTextField()) {
                saveInputFromlayout()
            } else {
                Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show()
            }

        }

        button_verb_clear.setOnClickListener {
            clearTextFields()
        }
    }

    private fun saveInputFromlayout() {
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
            currentTimestamp = Utils.getTimeStamp()
        )
        verbViewModel.saveToDB(verb).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it > 0) {
                clearTextFields()
                Toast.makeText(context!!, "Successfully added!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getVerbType(): String {
        return radioGroup_verb.findViewById<RadioButton>(radioGroup_verb.checkedRadioButtonId)
            .text.toString()
    }

    private fun getAllTextFields(): ArrayList<EditText> {
        return arrayListOf(
            editText_verb_masu,
            editText_verb_masupast,
            editText_verb_masunegative,
            editText_verb_masupastnegative,
            editText_verb_englishWord,
            editText_verb_japaneseWord,
            editText_verb_hirakata,
            editText_verb_kanji
        )
    }

    private fun checkTextField(): Boolean {
        var isAllFilled = true
        for (i in 0 until getAllTextFields().size) {
            if (getAllTextFields()[i].text.isEmpty()) {
                isAllFilled = false
                break
            }
        }
        return isAllFilled
    }

    private fun clearTextFields() {
        getAllTextFields().forEach {
            it.text.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearTextFields()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VerbFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VerbAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
