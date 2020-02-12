package com.teamzmron.selfstudyapp.Helper

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.teamzmron.selfstudyapp.Adapters.WordsAdapter
import com.teamzmron.selfstudyapp.Room.Entity.Word
import com.teamzmron.selfstudyapp.ViewModel.WordViewModel

class SwipeToDeleteHelper(val wordViewModel: WordViewModel,adapter: WordsAdapter, dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(
    dragDirs, swipeDirs
) {
    private var wordsAdapter: WordsAdapter = adapter
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        Log.v("Swipe", "Swiped!")
       return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
       var pos = viewHolder.adapterPosition
        wordsAdapter.notifyItemRemoved(pos)
        var word = wordViewModel.getWordsFromRepo().value!![pos]
        wordViewModel.deleteWordById(Word(
            id = word.id,
            english = word.english,
            japanese = word.japanese,
            hiragana = word.hiragana,
            kanji = word.kanji,
            sentence = word.sentence
        ))
    }

}