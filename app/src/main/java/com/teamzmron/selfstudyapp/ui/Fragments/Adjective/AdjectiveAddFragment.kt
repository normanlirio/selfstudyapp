package com.teamzmron.selfstudyapp.ui.Fragments.Adjective

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ViewModel.AdjectiveViewModel
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import com.teamzmron.selfstudyapp.ui.Resource
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_adjective.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdjectiveAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdjectiveAddFragment : DaggerFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adjectiveViewModel: AdjectiveViewModel

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
        return inflater.inflate(R.layout.fragment_add_adjective, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adjectiveViewModel = ViewModelProvider(this, providerFactory).get(AdjectiveViewModel::class.java)

        radio_i.isChecked = true

        button_adj_save.setOnClickListener {
            if (checkTextField()) {
                saveInputFromLayout()
            } else {
                Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show()
            }
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

       subscribeObservers(adjective)
    }

    private fun subscribeObservers(adjective: Adjective) {
        adjectiveViewModel.saveToDB(adjective).removeObservers(viewLifecycleOwner)
        adjectiveViewModel.saveToDB(adjective).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v("NounAddFragment", "subscribeObservers: Loading..")
                    }
                    Resource.Status.SUCCESS -> {
                        clearTextFields()
                        Log.v("NounAddFragment", "subscribeObservers: Success.. ${it.data}")

                    }
                    Resource.Status.ERROR -> {
                        Log.v("NounAddFragment", "subscribeObservers: Oops something went wrong. ${it.message}")
                    }
                }
            }
        })
    }


    private fun getAdjType(): String {
        return radioGroup_adj.findViewById<RadioButton>(radioGroup_adj.checkedRadioButtonId).text.toString()
    }

    private fun getAllTextFields(): ArrayList<EditText> {
        return arrayListOf(
            editText_adj_negative,
            editText_adj_past,
            editText_adj_pastnegative,
            editText_adj_englishWord,
            editText_adj_japaneseWord,
            editText_adj_hirakata,
            editText_adj_kanji
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
         * @return A new instance of fragment AdjectiveFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdjectiveAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
