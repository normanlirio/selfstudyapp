package com.teamzmron.selfstudyapp.Helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.teamzmron.selfstudyapp.Adapters.WordsAdapter
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ViewModel.WordViewModel

class SwipeToDeleteHelper(lifecycleOwner: LifecycleOwner, val wordViewModel: WordViewModel,adapter: WordsAdapter, dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(
    dragDirs, swipeDirs
) {
    private var wordsAdapter: WordsAdapter = adapter
    var wordsList : ArrayList<Noun> = ArrayList()
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
       return false
    }

    init {
        wordViewModel.getWordsFromRepo().observe(lifecycleOwner, Observer<List<Noun>> {
            wordsList.clear()
            if(it.isNotEmpty()) {
                wordsList.addAll(it)
            }
        })
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
       var pos = viewHolder.adapterPosition
        wordsAdapter.notifyItemRemoved(pos)
        wordViewModel.deleteWordById(Noun(
            id = wordsList[pos].id,
            english = wordsList[pos].english,
            japanese = wordsList[pos].japanese,
            hiragana = wordsList[pos].hiragana,
            kanji = wordsList[pos].kanji,
            sentence = wordsList[pos].sentence
        ))

    }

}