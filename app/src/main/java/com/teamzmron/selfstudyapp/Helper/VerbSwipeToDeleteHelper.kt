package com.teamzmron.selfstudyapp.Helper

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.teamzmron.selfstudyapp.Adapters.VerbAdapter
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ViewModel.VerbViewModel

class VerbSwipeToDeleteHelper(lifecycleOwner: LifecycleOwner, val verbViewModel: VerbViewModel, adapter: VerbAdapter, dragDirs: Int, swipeDirs: Int)
    : ItemTouchHelper.SimpleCallback(
    dragDirs, swipeDirs) {

    private var verbAdapter: VerbAdapter = adapter
    var verbList : ArrayList<Verb> = ArrayList()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }


    init {
//        verbViewModel.getVerbsFromRepo().observe(lifecycleOwner, Observer<List<Verb>> {
//            verbList.clear()
//            if(it.isNotEmpty()) {
//                verbList.addAll(it)
//            }
//        })
    }



    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var pos = viewHolder.adapterPosition
        verbAdapter.notifyItemRemoved(pos)

        verbViewModel.deleteVerb(
            Verb(
                verbId = verbList[pos].verbId,
                verbType = verbList[pos].verbType,
                masu = verbList[pos].masu,
                masuNegative = verbList[pos].masuNegative,
                masuPast = verbList[pos].masuPast,
                masuPastNegative = verbList[pos].masuPastNegative,
                englishWord = verbList[pos].englishWord,
                japaneseWord = verbList[pos].japaneseWord,
                hiraganaForm = verbList[pos].hiraganaForm,
                kanjiForm = verbList[pos].kanjiForm,
                currentTimestamp = verbList[pos].currentTimestamp
            )
        )

    }

}