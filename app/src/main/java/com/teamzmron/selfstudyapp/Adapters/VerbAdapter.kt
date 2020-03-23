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
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ViewModel.VerbViewModel

class VerbAdapter(
    context: Context,
    verbViewModel: VerbViewModel,
    lifecycle: LifecycleOwner,
    var clickListener: OnVerbClickListener
) : RecyclerView.Adapter<VerbAdapter.VerbViewHolder>() {

    private var context: Context? = null
    private var isAscendingAlpha = true
    private var isAscendingTime = true

    companion object {
        var verbList: ArrayList<Verb> = ArrayList()
    }

    init {
        this.context = context
        verbViewModel.getVerbsFromRepo().observe(lifecycle, Observer<List<Verb>> {
            verbList.clear()
            if (it.isNotEmpty()) {
                verbList.addAll(it)
            }
            notifyDataSetChanged()
        })
    }

    interface OnVerbClickListener {
        fun onVerbClick(id: Int)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerbViewHolder {
        return VerbViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_verb_items, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return verbList.size
    }

    override fun onBindViewHolder(holder: VerbViewHolder, position: Int) {
        holder.tvEnglish!!.text = verbList[position].englishWord
        holder.tvJapanese!!.text = verbList[position].japaneseWord
        holder.tvType!!.text = verbList[position].verbType
    }


    inner class VerbViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var tvEnglish: TextView? = null
        var cvParent: CardView? = null
        var tvJapanese: TextView? = null
        var tvType: TextView? = null

        init {
            tvEnglish = itemView.findViewById(R.id.wordlist_english)
            tvJapanese = itemView.findViewById(R.id.wordlist_japanese)
            cvParent = itemView.findViewById(R.id.cardView_wordlist_parent)
            tvType = itemView.findViewById(R.id.wordlist_verbtype)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            clickListener.onVerbClick(verbList[adapterPosition].verbId!!)
        }

    }
}