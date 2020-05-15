package com.teamzmron.selfstudyapp.ui.Fragments


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.Adapters.NounAdapter
import com.teamzmron.selfstudyapp.Adapters.ViewPagerAdapter
import com.teamzmron.selfstudyapp.ui.Fragments.Adjective.AdjectiveHomeFragment
import com.teamzmron.selfstudyapp.ui.Fragments.noun.NounHomeFragment
import com.teamzmron.selfstudyapp.ui.Fragments.verb.VerbHomeFragment
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.ViewModel.*
import com.teamzmron.selfstudyapp.ui.activities.HomeActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_word.*
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
open class Home : BaseFragment() {

    private lateinit var wordViewModel: WordViewModel


    private val NOUN = "Noun"
    private val VERB = "Verb"
    private val ADJ = "Adjective"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var homeActivity: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setUpViewpager()
        initViewModels()

        initTabLayout()
        Log.v("Home", "onViewCreated")


    }

    private fun initTabLayout() {
        tablayout.setupWithViewPager(viewpager)
    }


    private fun setUpViewpager() {
        val adapter = ViewPagerAdapter((context as AppCompatActivity).supportFragmentManager)
        adapter.addFragment(NounHomeFragment(), NOUN)
        adapter.addFragment(VerbHomeFragment(), VERB)
        adapter.addFragment(AdjectiveHomeFragment(), ADJ)
        viewpager.adapter = adapter
        viewpager.offscreenPageLimit = 6
    }


    private fun initViewModels() {
        wordViewModel = ViewModelProvider(this, providerFactory).get(WordViewModel::class.java)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.quiz -> {
                true
            }
            R.id.clearAllNoun -> {
                promptBeforeClearingWords(NOUN)
                true
            }
            R.id.clearAllVerb -> {
                promptBeforeClearingWords(VERB)
                true
            }
            R.id.clearAllAdj -> {
                promptBeforeClearingWords(ADJ)
                true
            }
            R.id.clearAll -> {
                promptBeforeClearingWords("ALL")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun promptBeforeClearingWords(category: String) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setMessage("Are you sure?")

        alertDialog.setPositiveButton("YES"
        ) { _, _ ->
            when(category) {
                NOUN -> {
                    wordViewModel.deleteAllNoun()
                }
                VERB -> {
                    wordViewModel.deleteAllVerb()
                }
                ADJ -> {
                    wordViewModel.deleteAllAdjectives()
                }
                "ALL" -> {
                    wordViewModel.deleteAllWordsFromRepo()
                }
            }

        }

        alertDialog.setNegativeButton("NO"
        ) { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.create().show()
    }






}
