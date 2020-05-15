package com.teamzmron.selfstudyapp.ui.Fragments.noun

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamzmron.selfstudyapp.Adapters.NounAdapter
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import com.teamzmron.selfstudyapp.ui.Resource
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_noun_home.*
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
class NounHomeFragment : DaggerFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var nounViewModel: NounViewModel
    private val TAG = "NounHomeFragment"


    @Inject
    lateinit var nounAdapter: NounAdapter

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
        Log.v("NounHomeFragment", "onCreateView: set OnCreateView!")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noun_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nounViewModel = ViewModelProvider(this, providerFactory).get(NounViewModel::class.java)
        Log.v("NounHomeFragment", "onActivityCreated: set onViewCreated!")

        subscribeObservers()
        initRecyclerView()


    }


    private fun subscribeObservers() {
        nounViewModel.getWordsFromRepo().removeObservers(viewLifecycleOwner)
        nounViewModel.getWordsFromRepo().observe(viewLifecycleOwner, Observer {
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
                        Log.v("NounHomeFragment", "subscribeObservers: Oops something went wrong. ${it.message}")
                    }
                }
            }
        })


    }

    private fun initRecyclerView() {

        recycler_nounhome.layoutManager = LinearLayoutManager(context)
        recycler_nounhome.adapter = nounAdapter
        registerForContextMenu(recycler_nounhome)


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



    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if(v == recycler_nounhome) {
            val inflater: MenuInflater =  requireActivity().menuInflater
            inflater.inflate(R.menu.longclick_menu, menu)
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit -> {
                Log.v(TAG, "onContextItemSelected: Edit ${nounAdapter.getNoun(item.groupId).english  }")
            }
            R.id.delete -> {
                deleteNoun(nounAdapter.getNoun(item.groupId), item.groupId)


                Log.v(TAG, "onContextItemSelected: Delete")
            }
        }
        return super.onContextItemSelected(item)

    }

    private fun deleteNoun(noun : Noun, id: Int) {
        nounViewModel.deleteNounById(noun)
        nounAdapter.notifyItemChanged(id)
        nounViewModel.getDeleteResult().removeObservers(this)
        nounViewModel.getDeleteResult().observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.v("NounHomeFragment", "subscribeObservers Delete: Loading..")
                    }
                    Resource.Status.SUCCESS -> {
                        subscribeObservers()
                        Log.v("NounHomeFragment", "subscribeObservers Delete: Success..")

                    }
                    Resource.Status.ERROR -> {
                        Log.v("NounHomeFragment", "subscribeObservers Delete: Oops something went wrong. ${it.message}")
                    }
                }
            }
        })
    }




}
