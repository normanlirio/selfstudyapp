package com.teamzmron.selfstudyapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.Helper.Utils

import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.ViewModel.AdjectiveViewModel
import kotlinx.android.synthetic.main.fragment_adjective.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdjectiveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdjectiveFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adjectiveViewModel: AdjectiveViewModel

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
        return inflater.inflate(R.layout.fragment_adjective, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adjectiveViewModel = ViewModelProvider(this).get(AdjectiveViewModel::class.java)

        button_adj_save.setOnClickListener {
            saveInputFromLayout()
        }

        button_adj_clear.setOnClickListener {
            clearTextFields()
        }

    }

    private fun saveInputFromLayout() {
        val adjType = if (getAdjType().contains("な")) "na" else "i"
        val adjective = Adjective(
            adjType = adjType,
            adjNegative = editText_adj_negative.text.toString(),
            adjPast = editText_adj_past.text.toString(),
            adjPastNegative = editText_adj_pastnegative.text.toString(),
            englishWord = editText_adj_englishWord.text.toString(),
            japaneseWord = editText_adj_japaneseWord.text.toString(),
            hiraganaForm = editText_adj_hirakata.text.toString(),
            kanjiForm = editText_adj_kanji.text.toString(),
            currentTimestamp = Utils.getTimeStamp()
        )

        adjectiveViewModel.saveToDB(adjective)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if (it > 0) {
                    clearTextFields()
                    Toast.makeText(context!!, "Successfully added!", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun getAdjType(): String {
        return radioGroup_adj.findViewById<RadioButton>(radioGroup_adj.checkedRadioButtonId).text.toString()
    }



    private fun clearTextFields() {
        editText_adj_negative.text.clear()
        editText_adj_past.text.clear()
        editText_adj_pastnegative.text.clear()
        editText_adj_englishWord.text.clear()
        editText_adj_japaneseWord.text.clear()
        editText_adj_hirakata.text.clear()
        editText_adj_kanji.text.clear()
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
         * @return A new instance of fragment AdjectiveFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdjectiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
