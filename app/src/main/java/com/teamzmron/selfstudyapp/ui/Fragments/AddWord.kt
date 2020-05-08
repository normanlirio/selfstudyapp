package com.teamzmron.selfstudyapp.ui.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.Adapters.ViewPagerAdapter
import com.teamzmron.selfstudyapp.ui.Fragments.Adjective.AdjectiveFragment
import com.teamzmron.selfstudyapp.ui.Fragments.noun.NounFragment
import com.teamzmron.selfstudyapp.ui.Fragments.verb.VerbFragment
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.ViewModel.AdjectiveViewModel
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import com.teamzmron.selfstudyapp.ViewModel.PageViewModel
import com.teamzmron.selfstudyapp.ViewModel.VerbViewModel
import kotlinx.android.synthetic.main.fragment_add_word.*

/**
 * A simple [Fragment] subclass.
 */
class AddWord : Fragment() {

    private lateinit var nounViewModel: NounViewModel
    private lateinit var pageViewModel: PageViewModel
    private lateinit var adjectiveViewModel: AdjectiveViewModel
    private lateinit var verbViewModel: VerbViewModel

    private val NOUN = "noun"
    private val VERB = "verb"
    private val ADJ = "adj"
    private var isNoun = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_word, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModels()
        initViewpager()

        tablayout_addword.setupWithViewPager(viewpager_addword)
    }

    private fun initViewpager() {
        val adapter = ViewPagerAdapter((context as AppCompatActivity).supportFragmentManager)
        adapter.addFragment(NounFragment(), "Noun")
        adapter.addFragment(VerbFragment(), "Verb")
        adapter.addFragment(AdjectiveFragment(), "Adjective")
        viewpager_addword.adapter = adapter
        viewpager_addword.offscreenPageLimit = 6
    }

    private fun initViewModels() {
        nounViewModel = ViewModelProvider(this).get(NounViewModel::class.java)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        adjectiveViewModel = ViewModelProvider(this).get(AdjectiveViewModel::class.java)
        verbViewModel = ViewModelProvider(this).get(VerbViewModel::class.java)
    }



}

