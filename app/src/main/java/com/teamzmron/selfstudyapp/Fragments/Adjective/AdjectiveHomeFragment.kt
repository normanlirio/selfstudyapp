package com.teamzmron.selfstudyapp.Fragments.Adjective

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamzmron.selfstudyapp.Adapters.AdjectiveAdapter
import com.teamzmron.selfstudyapp.Helper.AdjectiveSwipeToDeleteHelper

import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.ViewModel.AdjectiveViewModel
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_adjective_home.*
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
class AdjectiveHomeFragment : DaggerFragment(), AdjectiveAdapter.OnAdjectiveClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adjectiveViewModel: AdjectiveViewModel

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
        return inflater.inflate(R.layout.fragment_adjective_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModels()
        initRecyclerView()
    }

    private fun initViewModels() {
        adjectiveViewModel = ViewModelProvider(this, providerFactory).get(AdjectiveViewModel::class.java)
    }

    private fun initRecyclerView() {
        recycler_adjhome.layoutManager = LinearLayoutManager(context)
        recycler_adjhome.adapter = adapter

        var itemTouchHelper = ItemTouchHelper(
            AdjectiveSwipeToDeleteHelper(
                this,
                adjectiveViewModel,
                adapter, 0, ItemTouchHelper.RIGHT
            )
        )

        itemTouchHelper.attachToRecyclerView(recycler_adjhome)
        adapter.notifyDataSetChanged()
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

    override fun onAdjectiveClick(id: Int) {

    }
}
