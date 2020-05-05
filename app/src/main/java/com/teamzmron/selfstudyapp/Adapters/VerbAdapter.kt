package com.teamzmron.selfstudyapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ViewModel.VerbViewModel

class VerbAdapter : RecyclerView.Adapter<VerbAdapter.VerbViewHolder>() {

    private var context: Context? = null
    private var isAscendingAlpha = true
    private var isAscendingTime = true

    private var verbList: ArrayList<Verb> = ArrayList()
    private var clickListener: OnVerbClickListener? = null

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

    fun setVerbs(verbs : List<Verb>) {
        this.verbList.clear()
        this.verbList.addAll(verbs)
        notifyDataSetChanged()
    }

    fun setOnVerbClickListener(onVerbClickListener: OnVerbClickListener) {
        this.clickListener = onVerbClickListener
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
            clickListener!!.onVerbClick(verbList[adapterPosition].verbId!!)
        }

    }
}