package com.teamzmron.selfstudyapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Adjective
import com.teamzmron.selfstudyapp.ViewModel.AdjectiveViewModel

class AdjectiveAdapter(
    context: Context,
    adjectiveViewModel: AdjectiveViewModel,
    lifecycle: LifecycleOwner,
    var clickListener: OnAdjectiveClickListener
) : RecyclerView.Adapter<AdjectiveAdapter.AdjectiveViewHolder>() {
    private var context: Context? = null

    companion object {
        var adjList: ArrayList<Adjective> = ArrayList()
    }

    init {
        this.context = context
        adjectiveViewModel.getAdjectiveFromRepo().observe(lifecycle, Observer<List<Adjective>> {
            adjList.clear()
            if (it.isNotEmpty()) {
                adjList.addAll(it)
            }
            notifyDataSetChanged()
        })
    }


    interface OnAdjectiveClickListener {
        fun onAdjectiveClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdjectiveViewHolder {
        return AdjectiveViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_adjective_items, parent, false)
        )
    }

    override fun getItemCount(): Int {
       return adjList.size
    }

    override fun onBindViewHolder(holder: AdjectiveViewHolder, position: Int) {
        holder.tvEnglish!!.text = adjList[position].englishWord
        holder.tvJapanese!!.text = adjList[position].japaneseWord
        holder.tvType!!.text = adjList[position].adjType
    }


    inner class AdjectiveViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvEnglish: TextView? = null
        var cvParent: CardView? = null
        var tvJapanese: TextView? = null
        var tvType: TextView? = null
        init {
            tvEnglish = itemView.findViewById(R.id.wordlist_english_adj)
            tvJapanese = itemView.findViewById(R.id.wordlist_japanese_adj)
            cvParent = itemView.findViewById(R.id.cardView_wordlist_parent_adj)
            tvType = itemView.findViewById(R.id.wordlist_adjtype)
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
        }

    }

}