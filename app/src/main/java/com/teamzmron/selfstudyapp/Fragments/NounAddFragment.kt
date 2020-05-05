package com.teamzmron.selfstudyapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_adjective.*
import kotlinx.android.synthetic.main.fragment_noun.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NounFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NounFragment : DaggerFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var nounViewModel: NounViewModel

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
        return inflater.inflate(R.layout.fragment_noun, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nounViewModel = ViewModelProvider(this).get(NounViewModel::class.java)


        button_noun_save.setOnClickListener{
            if (checkTextField()) {
                saveInputFromLayout()
            } else {
                Toast.makeText(context!!, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
        }

        button_noun_clear.setOnClickListener {
            clearTextFields()
        }
    }


    private fun saveInputFromLayout() {
        val noun = Noun(
            japanese = editText_noun_japanese.text.toString(),
            english = editText_noun_english.text.toString(),
            hiragana = editText_noun_hiragana.text.toString(),
            kanji = editText_noun_kanji.text.toString(),
            timestamp = Utils.getTimeStamp()
        )
        nounViewModel.saveToDB(noun).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it > 0) {
                clearTextFields()
                Toast.makeText(context!!, "Successfully added!", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun getAllTextFields(): ArrayList<EditText> {
        return arrayListOf(
            editText_noun_japanese,
                    editText_noun_english,
                    editText_noun_hiragana,
                    editText_noun_kanji
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
         * @return A new instance of fragment NounFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NounFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
