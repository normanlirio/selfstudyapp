package com.teamzmron.selfstudyapp.Fragments


import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamzmron.selfstudyapp.Adapters.ViewPagerAdapter
import com.teamzmron.selfstudyapp.Adapters.WordsAdapter
import com.teamzmron.selfstudyapp.Helper.SwipeToDeleteHelper
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.ViewModel.PageViewModel
import com.teamzmron.selfstudyapp.ViewModel.WordDetailsViewModel
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.sql.Timestamp
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment(), WordsAdapter.OnWordClickListener {

    private lateinit var nounViewModel: NounViewModel
    private lateinit var pageViewModel: PageViewModel
    private val wordDetailsViewModel: WordDetailsViewModel by activityViewModels()
    private var isGridView = false

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
        adapter.addFragment(NounFragment(), "Noun")
        adapter.addFragment(VerbFragment(), "Verb")
        adapter.addFragment(AdjectiveFragment(), "Adjective")
        viewpager.adapter = adapter
        viewpager.offscreenPageLimit = 6
    }


    private fun initViewModels() {
        nounViewModel = ViewModelProvider(this).get(NounViewModel::class.java)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                pageViewModel.getFragmentTransaction(context!!)
                    .replace(pageViewModel.getContainer(), Home())
                    .commit()
                true
            }
            R.id.add -> {
                pageViewModel.getFragmentTransaction(context!!)
                    .add(pageViewModel.getContainer(), AddWord(),"addWord")
                    .commit()
                true
            }
            R.id.quiz -> {
                pageViewModel.getFragmentTransaction(context!!)
                    .add(pageViewModel.getContainer(), QuizSettings(),"quizSettings")
                    .commit()
                true
            }
            R.id.clearAll -> {
                promptBeforeClearingWords()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun promptBeforeClearingWords() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setMessage("Are you sure?")

        alertDialog.setPositiveButton("YES"
        ) { _, _ ->
            nounViewModel.deleteAllWords()
        }

        alertDialog.setNegativeButton("NO"
        ) { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.create().show()
    }



    override fun onWordClick(id: Int) {
        wordDetailsViewModel.setMutableId(id)
        pageViewModel.getFragmentTransaction(context!!)
            .add(R.id.fragment_container, WordDetails(), "WordDetails")
            .commit()
    }


}
