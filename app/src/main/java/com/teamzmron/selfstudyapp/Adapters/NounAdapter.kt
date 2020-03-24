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
import com.teamzmron.selfstudyapp.Room.Entity.Noun
import com.teamzmron.selfstudyapp.ViewModel.NounViewModel
import kotlin.collections.ArrayList

class NounAdapter(context : Context, nounViewModel: NounViewModel, lifecycle : LifecycleOwner, var clickListener: OnNounClickListener) : RecyclerView.Adapter<NounAdapter.WordsViewHolder>() {
    private var context: Context? = null

    companion object {
         var wordsList : ArrayList<Noun> = ArrayList()
    }


    init {
        this.context = context
        nounViewModel.getWordsFromRepo().observe(lifecycle, Observer<List<Noun>> {
            wordsList.clear()
            if(it.isNotEmpty()) {
                wordsList.addAll(it)
            }
            notifyDataSetChanged()
        })
    }

    interface OnNounClickListener {
        fun onNounClick(id: Int)

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
            clickListener.onNounClick(wordsList[adapterPosition].id!!)
        }

    }

}