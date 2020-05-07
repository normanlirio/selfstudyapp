package com.teamzmron.selfstudyapp.Fragments


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.teamzmron.selfstudyapp.Adapters.NounAdapter
import com.teamzmron.selfstudyapp.Adapters.ViewPagerAdapter
import com.teamzmron.selfstudyapp.Fragments.Adjective.AdjectiveHomeFragment
import com.teamzmron.selfstudyapp.Fragments.noun.NounHomeFragment
import com.teamzmron.selfstudyapp.Fragments.verb.VerbHomeFragment
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.ViewModel.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class Home : DaggerFragment(), NounAdapter.OnNounClickListener {

    private lateinit var nounViewModel: NounViewModel
    private lateinit var wordViewModel: WordViewModel


    private val NOUN = "Noun"
    private val VERB = "Verb"
    private val ADJ = "Adjective"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

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

        initViewModels()
        setUpViewpager()
        initTabLayout()

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

    }


    private fun initViewModels() {
        nounViewModel = ViewModelProvider(this, providerFactory).get(NounViewModel::class.java)
        wordViewModel = ViewModelProvider(this, providerFactory).get(WordViewModel::class.java)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {

                true
            }
            R.id.add -> {


                true
            }
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



    override fun onNounClick(id: Int) {

//        pageViewModel.getFragmentTransaction(context!!)
//            .add(R.id.fragment_container, WordDetails(), "WordDetails")
//            .commit()
    }


}
