package com.teamzmron.selfstudyapp.Fragments


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamzmron.selfstudyapp.Adapters.WordsAdapter
import com.teamzmron.selfstudyapp.Helper.SwipeToDeleteHelper
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Database.WordDatabase
import com.teamzmron.selfstudyapp.Room.Entity.Word
import com.teamzmron.selfstudyapp.ViewModel.PageViewModel
import com.teamzmron.selfstudyapp.ViewModel.WordViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.sql.Timestamp
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment(), WordsAdapter.OnWordClickListener {

    private lateinit var wordViewModel : WordViewModel
    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var pageViewModel: PageViewModel

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

        wordViewModel.displayWordsToList().observe(this, Observer<List<Word>> {
            Log.v("Observer", "Observed")
            wordsAdapter.notifyDataSetChanged()
        })

    }

    private fun initViewModels() {
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        wordViewModel.init()
        var dbInstance = WordDatabase.getDatabasenIstance(context!!)
        wordViewModel.setInstanceOfDB(dbInstance)
    }
    private fun initRecyclerView() {
        wordsAdapter = WordsAdapter(context!!, wordViewModel, this, this)
        recycler_home.layoutManager = LinearLayoutManager(context!!)
        recycler_home.adapter = wordsAdapter

        var itemTouchHelper = ItemTouchHelper(SwipeToDeleteHelper(wordViewModel,
            wordsAdapter,0, ItemTouchHelper.RIGHT))

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

        var etJapanese = dialog.findViewById(R.id.editText_addVocabulary_addword) as TextView
        var etEnglish = dialog.findViewById(R.id.editText_addVocabulary_addEnglish) as TextView
        var etHiraKata = dialog.findViewById(R.id.editText_addVocabulary_hiragana) as TextView
        var etKanji = dialog.findViewById(R.id.editText_addVocabulary_kanji) as TextView
        var etSampleSentence = dialog.findViewById(R.id.editText_addVocabulary_sentence) as TextView
        var btnCancel = dialog.findViewById<TextView>(R.id.button_addForm_cancel) as Button
        var btnSave = dialog.findViewById<TextView>(R.id.button_addForm_save) as Button

        btnSave.setOnClickListener {
            Log.v("Timestamp", getTimeStamp())
            dialog.dismiss()
            wordViewModel.saveToDB(Word(japanese = etJapanese.text.toString(),
                english = etEnglish.text.toString(),
                hiragana = etHiraKata.text.toString(),
                kanji = etKanji.text.toString(),
                sentence = etSampleSentence.text.toString(),
                timestamp = getTimeStamp()
            ))

        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()


    }


    private fun getTimeStamp() : String {
        val date = Date()
        val time: Long = date.time
        val ts = Timestamp(time)
        return ts.toString()
    }

    override fun onWordClick(id: Int) {
        Log.v("CLICK","LISTENER :" + id)
        val bundle : Bundle = Bundle()
        bundle.putInt("id", id)
        pageViewModel.setBundle(bundle)
        val fragment = WordDetails()
        fragment.arguments = pageViewModel.getBundle().value
        pageViewModel.setFragment(fragment)
        pageViewModel.getFragmentTransaction(context!!)
            .add(R.id.fragment_container, pageViewModel.getFragment().value!!, "WordDetails")
            .commit()
    }


}
