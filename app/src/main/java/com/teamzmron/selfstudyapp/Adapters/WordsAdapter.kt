package com.teamzmron.selfstudyapp.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Word
import com.teamzmron.selfstudyapp.ViewModel.WordViewModel

class WordsAdapter(context : Context, wordViewModel: WordViewModel, lifecycle : LifecycleOwner, var clickListener: OnWordClickListener) : RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {
    private var context: Context? = null

    companion object {
        private var wordsList : ArrayList<Word> = ArrayList()
    }

    init {
        this.context = context
        wordViewModel.displayWordsToList().observe(lifecycle, Observer<List<Word>> {
            wordsList.clear()
            if(!it.isEmpty()) {
                wordsList.addAll(it)
            }
            notifyDataSetChanged()
        })
    }

    interface OnWordClickListener {
        fun onWordClick(position: Int)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        return WordsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.wordslist, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return wordsList.size
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.etEnglish!!.text = wordsList[position].english
        holder.etTimeStamp!!.text = wordsList[position].timestamp

    }

    inner class WordsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var etEnglish: TextView? = null
        var etTimeStamp: TextView? = null
        init {
            Log.v("ViewHolder", "Viewholder")
            etEnglish = itemView.findViewById(R.id.wordlist_english)
            etTimeStamp = itemView.findViewById(R.id.wordlist_timestamp)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            clickListener.onWordClick(wordsList[adapterPosition].id!!)
        }

    }

}