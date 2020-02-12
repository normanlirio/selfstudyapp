package com.teamzmron.selfstudyapp.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Word
import com.teamzmron.selfstudyapp.ViewModel.WordViewModel

class WordsAdapter(context : Context, wordViewModel: WordViewModel, lifecycle : LifecycleOwner, var clickListener: OnWordClickListener) : RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {
    private var context: Context? = null

    companion object {
         var wordsList : ArrayList<Word> = ArrayList()
    }

    init {
        this.context = context
        wordViewModel.getWordsFromRepo().observe(lifecycle, Observer<List<Word>> {
            wordsList.clear()
            if(it.isNotEmpty()) {
                wordsList.addAll(it)
            }
            notifyDataSetChanged()
        })
    }

    interface OnWordClickListener {
        fun onWordClick(id: Int)

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

    }
    inner class WordsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var etEnglish: TextView? = null
        var cvParent: CardView? = null
        init {
            Log.v("ViewHolder", "Viewholder")
            etEnglish = itemView.findViewById(R.id.wordlist_english)
            cvParent = itemView.findViewById(R.id.cardView_wordlist_parent)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            clickListener.onWordClick(wordsList[adapterPosition].id!!)
        }

    }

}