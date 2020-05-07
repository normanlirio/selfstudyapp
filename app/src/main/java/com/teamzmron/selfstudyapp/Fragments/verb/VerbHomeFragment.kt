package com.teamzmron.selfstudyapp.Fragments.verb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamzmron.selfstudyapp.Adapters.VerbAdapter
import com.teamzmron.selfstudyapp.Helper.VerbSwipeToDeleteHelper
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.ViewModel.VerbViewModel
import com.teamzmron.selfstudyapp.ViewModel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_verb_home.*
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
class VerbHomeFragment : DaggerFragment(), VerbAdapter.OnVerbClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var verbViewModel: VerbViewModel

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
        return inflater.inflate(R.layout.fragment_verb_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModels()
        initRecyclerView()
    }

    private fun initViewModels() {
        verbViewModel = ViewModelProvider(this, providerFactory).get(VerbViewModel::class.java)
    }

    private fun initRecyclerView() {
        recycler_verbhome.layoutManager = LinearLayoutManager(context)

        recycler_verbhome.adapter = verbAdapter

        var itemTouchHelper = ItemTouchHelper(
            VerbSwipeToDeleteHelper(
                this,
                verbViewModel,
                verbAdapter, 0, ItemTouchHelper.RIGHT
            )
        )

        itemTouchHelper.attachToRecyclerView(recycler_verbhome)
        verbAdapter.notifyDataSetChanged()

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

    override fun onVerbClick(id: Int) {

    }
}
