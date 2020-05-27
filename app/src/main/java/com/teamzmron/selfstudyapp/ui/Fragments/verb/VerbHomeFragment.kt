package com.teamzmron.selfstudyapp.ui.Fragments.verb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamzmron.selfstudyapp.Adapters.VerbAdapter
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.VERB_DELETE_ID
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.VERB_EDIT_ID
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.VERB_VIEW_ID
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.Resource
import kotlinx.android.synthetic.main.fragment_home_verb.*
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VerbHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VerbHomeFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = javaClass.simpleName


    @Inject
    lateinit var verbAdapter: VerbAdapter

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
        return inflater.inflate(R.layout.fragment_home_verb, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()

        subscribeObservers()
    }

    private fun subscribeObservers() {
        verbViewModel.getVerbsFromRepo().removeObservers(viewLifecycleOwner)
        verbViewModel.getVerbsFromRepo().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v(TAG, "subscribeObservers: Loading..")
                    }
                    Resource.Status.SUCCESS -> {
                        Log.v(TAG, "subscribeObservers: Success..")
                        verbAdapter.setVerbs(it.data!!)
                    }
                    Resource.Status.ERROR -> {
                        Log.v(TAG, "subscribeObservers: Oops something went wrong. ${it.message}")
                    }
                }
            }
        })
    }



    private fun initRecyclerView() {
        registerForContextMenu(recycler_verbhome)
        recycler_verbhome.layoutManager = LinearLayoutManager(context)
        recycler_verbhome.adapter = verbAdapter


    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val verb = verbAdapter.getVerb(item.order)
        sharedViewModel.setMutableVerb(verb)
        return when(item.itemId) {
            VERB_VIEW_ID -> {
                Utils.navigateToOtherFragment(requireActivity(), R.id.verbView)
                true
            }
            VERB_EDIT_ID -> {
                Utils.navigateToOtherFragment(requireActivity(), R.id.verbEdit)
                true
            }
            VERB_DELETE_ID -> {
                deleteVerb(verbAdapter.getVerb(item.order), item.order)
                true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }


    }

    private fun deleteVerb(verb : Verb, id: Int) {
        verbViewModel.deleteVerb(verb)

        verbViewModel.observeGetDeleteResult().removeObservers(this)
        verbViewModel.observeGetDeleteResult().observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v(TAG, "subscribeObservers Delete: Loading..")
                    }
                    Resource.Status.SUCCESS -> {
                        Log.v(TAG, "subscribeObservers Delete: ${id}")

                        verbAdapter.notifyItemChanged(id)
                        subscribeObservers()
                        Log.v(TAG, "subscribeObservers Delete: Success..")

                    }
                    Resource.Status.ERROR -> {
                        Log.v(TAG, "subscribeObservers Delete: Oops something went wrong. ${it.message}")
                    }
                }
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VerbListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VerbHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
