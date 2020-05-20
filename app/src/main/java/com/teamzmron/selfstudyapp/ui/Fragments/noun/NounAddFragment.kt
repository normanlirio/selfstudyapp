package com.teamzmron.selfstudyapp.ui.Fragments.noun

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.Adapters.NounAdapter
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.Helper.Utils.Companion.hideKeyboard
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.Resource
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_noun.*
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NounAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NounAddFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    @Inject
    lateinit var nounAdapter: NounAdapter

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
        return inflater.inflate(R.layout.fragment_add_noun, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_noun_save.setOnClickListener{
            if (checkTextField()) {
                saveInputFromLayout()
            } else {
                Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show()
            }
            it.hideKeyboard()
        }

        button_noun_clear.setOnClickListener {
            clearTextFields()
            it.hideKeyboard()
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
        subscribeObservers(noun)

    }

    private fun subscribeObservers(noun : Noun) {
        nounViewModel.saveToDB(noun)
        nounViewModel.observeSaveResult().removeObservers(viewLifecycleOwner)
        nounViewModel.observeSaveResult().observe(viewLifecycleOwner, Observer {
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
            NounAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
