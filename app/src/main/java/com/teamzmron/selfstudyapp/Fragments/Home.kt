package com.teamzmron.selfstudyapp.Fragments


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamzmron.selfstudyapp.Adapters.WordsAdapter
import com.teamzmron.selfstudyapp.Helper.SwipeToDeleteHelper
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Word
import com.teamzmron.selfstudyapp.ViewModel.PageViewModel
import com.teamzmron.selfstudyapp.ViewModel.WordDetailsViewModel
import com.teamzmron.selfstudyapp.ViewModel.WordViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment(), WordsAdapter.OnWordClickListener {

    private lateinit var wordViewModel: WordViewModel
    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var pageViewModel: PageViewModel
    private lateinit var wordDetailsViewModel: WordDetailsViewModel


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
        initRecyclerView()

        wordViewModel.getWordsFromRepo().observe(this, Observer {
            wordsAdapter.notifyDataSetChanged()
        })
    }


    private fun initViewModels() {
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        wordDetailsViewModel = ViewModelProviders.of(activity!!).get(WordDetailsViewModel::class.java)
    }

    private fun initRecyclerView() {
        var isGridView = false

        wordsAdapter = WordsAdapter(context!!, wordViewModel, this, this)
        recycler_home.layoutManager = LinearLayoutManager(context!!)

        image_home_gridlist.setOnClickListener {
            if(isGridView) {
                recycler_home.layoutManager = LinearLayoutManager(context!!)
                image_home_gridlist.background = ResourcesCompat.getDrawable(resources,R.drawable.ic_grid, null)
                isGridView = false
            } else {
                recycler_home.layoutManager = GridLayoutManager(context!!, 4)
                image_home_gridlist.background = ResourcesCompat.getDrawable(resources,R.drawable.ic_list, null)
                isGridView = true
            }
        }
        recycler_home.adapter = wordsAdapter

        var itemTouchHelper = ItemTouchHelper(
            SwipeToDeleteHelper(
                this,
                wordViewModel,
                wordsAdapter, 0, ItemTouchHelper.RIGHT
            )
        )

        itemTouchHelper.attachToRecyclerView(recycler_home)
        wordsAdapter.notifyDataSetChanged()

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                inflateAddForm()
                true
            }
            R.id.quiz -> {
                pageViewModel.setFragment(QuizSettings())
                pageViewModel.getFragmentTransaction(context!!)
                    .add(pageViewModel.getFragmentContainer_(), pageViewModel.getFragment().value!!)
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun inflateAddForm() {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_form)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp

        val etJapanese = dialog.findViewById(R.id.editText_addVocabulary_addword) as TextView
        val etEnglish = dialog.findViewById(R.id.editText_addVocabulary_addEnglish) as TextView
        val etHiraKata = dialog.findViewById(R.id.editText_addVocabulary_hiragana) as TextView
        val etKanji = dialog.findViewById(R.id.editText_addVocabulary_kanji) as TextView
        val etSampleSentence = dialog.findViewById(R.id.editText_addVocabulary_sentence) as TextView
        val btnCancel = dialog.findViewById<TextView>(R.id.button_addForm_cancel) as Button
        val btnSave = dialog.findViewById<TextView>(R.id.button_addForm_save) as Button

        btnSave.setOnClickListener {
            Log.v("Timestamp", getTimeStamp())
            dialog.dismiss()
            wordViewModel.saveToDB(
                Word(
                    japanese = etJapanese.text.toString(),
                    english = etEnglish.text.toString(),
                    hiragana = etHiraKata.text.toString(),
                    kanji = etKanji.text.toString(),
                    sentence = etSampleSentence.text.toString(),
                    timestamp = getTimeStamp()
                )
            )

        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun getTimeStamp(): String {
        val date = Date()
        val time: Long = date.time
        val ts = Timestamp(time)
        return ts.toString()
    }

    override fun onWordClick(id: Int) {
        wordDetailsViewModel.setMutableId(id)
        val bundle = Bundle()
        bundle.putInt("id", id)
        pageViewModel.setBundle(bundle)
        val fragment = WordDetails()
        fragment.arguments = pageViewModel.getBundle().value
        pageViewModel.setFragment(fragment)
        pageViewModel.getFragmentTransaction(context!!)
            .replace(R.id.fragment_container, pageViewModel.getFragment().value!!, "WordDetails")
            .commit()
    }


}
