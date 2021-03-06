package com.teamzmron.selfstudyapp.ui.Fragments.Adjective

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamzmron.selfstudyapp.Adapters.AdjectiveAdapter
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.ADJ_DELETE_ID
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.ADJ_EDIT_ID
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.ADJ_VIEW_ID
import com.teamzmron.selfstudyapp.Helper.Utils

import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import com.teamzmron.selfstudyapp.ui.Fragments.BaseFragment
import com.teamzmron.selfstudyapp.ui.Resource
import kotlinx.android.synthetic.main.fragment_home_adjective.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdjectiveHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdjectiveHomeFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = javaClass.simpleName

    @Inject
    lateinit var adapter: AdjectiveAdapter

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
        return inflater.inflate(R.layout.fragment_home_adjective, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        adjectiveViewModel.getAdjectiveFromRepo().removeObservers(viewLifecycleOwner)
        adjectiveViewModel.getAdjectiveFromRepo().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v(TAG, "subscribeObservers: Loading..")
                    }
                    Resource.Status.SUCCESS -> {
                        Log.v(TAG, "subscribeObservers: Success..")
                        adapter.setAdjectives(it.data!!)
                    }
                    Resource.Status.ERROR -> {
                        Log.v(TAG, "subscribeObservers: Oops something went wrong. ${it.message}")
                    }
                }
            }
        })
    }


    private fun initRecyclerView() {

        recycler_adjhome.layoutManager = LinearLayoutManager(context)
        recycler_adjhome.adapter = adapter
        registerForContextMenu(recycler_adjhome)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val adj = adapter.getAdjective(item.order)
        sharedViewModel.setMutableAdjective(adj)
        return when(item.itemId) {
            ADJ_VIEW_ID -> {
                Utils.navigateToOtherFragment(requireActivity(), R.id.adjView)
                true
            }
            ADJ_EDIT_ID -> {
                Utils.navigateToOtherFragment(requireActivity(), R.id.adjEdit)
                true
            }
           ADJ_DELETE_ID -> {
                deleteAdjective(adapter.getAdjective(item.order), item.order)
               true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }


    }

    private fun deleteAdjective(adj : Adjective, id: Int) {
        adjectiveViewModel.deleteAdjective(adj)

        adjectiveViewModel.observeGetDeleteResult().removeObservers(this)
        adjectiveViewModel.observeGetDeleteResult().observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v(TAG, "subscribeObservers Delete: Loading..")
                    }
                    Resource.Status.SUCCESS -> {
                        adapter.notifyItemChanged(id)
                        subscribeObservers()
                        Log.v(TAG, "subscribeObservers Delete: Success..")

                    }
                    Resource.Status.ERROR -> {
                        Log.v("NounHomeFragment", "subscribeObservers Delete: Oops something went wrong. ${it.message}")
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
         * @return A new instance of fragment AdjectiveListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdjectiveHomeFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
