package com.teamzmron.selfstudyapp.ui.Fragments.noun

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamzmron.selfstudyapp.Adapters.NounAdapter
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.NOUN_DELETE_ID
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.NOUN_EDIT_ID
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.NOUN_VIEW_ID
import com.teamzmron.selfstudyapp.Helper.Utils
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.Resource
import com.teamzmron.selfstudyapp.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_home_noun.*
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NounHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NounHomeFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val TAG = javaClass.simpleName

    @Inject
    lateinit var nounAdapter: NounAdapter

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var homeActivity: HomeActivity


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
        Log.v("NounHomeFragment", "onCreateView: set OnCreateView!")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_noun, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeObservers()
        initRecyclerView()

        homeActivity.unlockDrawer()


    }


    private fun subscribeObservers() {
        nounViewModel.getNounsFromRepo().removeObservers(viewLifecycleOwner)
        nounViewModel.getNounsFromRepo().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v("NounHomeFragment", "subscribeObservers: Loading..")
                    }
                    Resource.Status.SUCCESS -> {
                        Log.v("NounHomeFragment", "subscribeObservers: Success..")
                        nounAdapter.setNouns(it.data!!)
                    }
                    Resource.Status.ERROR -> {
                        Log.v("NounHomeFragment","subscribeObservers: Oops something went wrong. ${it.message}"
                        )
                    }
                }
            }
        })

    }

    private fun initRecyclerView() {
        nounAdapter.setContext(requireContext())
        recycler_nounhome.layoutManager = LinearLayoutManager(context)
        recycler_nounhome.adapter = nounAdapter
        registerForContextMenu(recycler_nounhome)

    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val noun = nounAdapter.getNoun(item.order)
        sharedViewModel.setMutableNoun(noun)
        when (item.itemId) {
            NOUN_VIEW_ID -> {
                Utils.navigateToOtherFragment(requireActivity(), R.id.nounView)
            }
            NOUN_EDIT_ID -> {
                Utils.navigateToOtherFragment(requireActivity(), R.id.nounEdit)
                Log.v(TAG, "onContextItemSelected: Edit ${noun.english}")
            }
            NOUN_DELETE_ID -> {
                Log.v(TAG, "onContextItemSelected: Delete ${item.groupId}")
                deleteNoun(noun, item.order)

            }
        }
        return super.onContextItemSelected(item)

    }

    private fun deleteNoun(noun: Noun, id: Int) {
        nounViewModel.deleteNoun(noun)

        nounViewModel.observeGetDeleteResult().removeObservers(this)
        nounViewModel.observeGetDeleteResult().observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v(TAG, "subscribeObservers Delete: Loading..")
                    }
                    Resource.Status.SUCCESS -> {
                        Log.v(TAG, "subscribeObservers Delete: ${id}")

                        nounAdapter.notifyItemChanged(id)
                        subscribeObservers()
                        Log.v(TAG, "subscribeObservers Delete: Success..")

                    }
                    Resource.Status.ERROR -> {
                        Log.v(
                            TAG,"subscribeObservers Delete: Oops something went wrong. ${it.message}"
                        )
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
         * @return A new instance of fragment NounListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NounHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}
