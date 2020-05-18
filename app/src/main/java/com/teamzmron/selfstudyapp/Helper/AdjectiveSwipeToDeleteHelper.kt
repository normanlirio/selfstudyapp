package com.teamzmron.selfstudyapp.Helper

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.teamzmron.selfstudyapp.Adapters.AdjectiveAdapter
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.ViewModel.AdjectiveViewModel

class AdjectiveSwipeToDeleteHelper(lifecycleOwner: LifecycleOwner, val adjectiveViewModel: AdjectiveViewModel, adapter: AdjectiveAdapter, dragDirs: Int, swipeDirs: Int)
     : ItemTouchHelper.SimpleCallback(
dragDirs, swipeDirs) {

    private var adjAdapter: AdjectiveAdapter = adapter
    var adjList : ArrayList<Adjective> = ArrayList()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
       return false
    }

    init {
//        adjectiveViewModel.getAdjectiveFromRepo().observe(lifecycleOwner, Observer<List<Adjective>> {
//            adjList.clear()
//            if(it.isNotEmpty()) {
//                adjList.addAll(it)
//            }
//        })
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var pos = viewHolder.adapterPosition
        adjAdapter.notifyItemRemoved(pos)

        adjectiveViewModel.deleteAdjective(
            Adjective(
                adjId = adjList[pos].adjId,
                adjType = adjList[pos].adjType,
                adjPast = adjList[pos].adjPast,
                adjNegative = adjList[pos].adjNegative,
                adjPastNegative = adjList[pos].adjPastNegative,
                englishWord = adjList[pos].englishWord,
                japaneseWord = adjList[pos].japaneseWord,
                hiraganaForm = adjList[pos].hiraganaForm,
                kanjiForm = adjList[pos].kanjiForm,
                currentTimestamp = adjList[pos].currentTimestamp
            )
        )
    }
}