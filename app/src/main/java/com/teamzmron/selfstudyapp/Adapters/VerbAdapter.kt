package com.teamzmron.selfstudyapp.Adapters

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.teamzmron.selfstudyapp.Helper.Constants
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.VERB_DELETE_ID
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.VERB_EDIT_ID
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Verb
import com.teamzmron.selfstudyapp.ViewModel.VerbViewModel

class VerbAdapter : RecyclerView.Adapter<VerbAdapter.VerbViewHolder>() {

    private var context: Context? = null
    private var verbList: ArrayList<Verb> = ArrayList()

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

    fun getVerb(position: Int) : Verb {
        return verbList[position]
    }


    inner class VerbViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        var tvEnglish: TextView? = null
        var cvParent: CardView? = null
        var tvJapanese: TextView? = null
        var tvType: TextView? = null

        init {
            tvEnglish = itemView.findViewById(R.id.wordlist_english)
            tvJapanese = itemView.findViewById(R.id.wordlist_japanese)
            cvParent = itemView.findViewById(R.id.cardView_wordlist_parent)
            tvType = itemView.findViewById(R.id.wordlist_verbtype)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            p1: View?,
            p2: ContextMenu.ContextMenuInfo?
        ) {

            menu!!.add(0, VERB_EDIT_ID, adapterPosition, "Edit")
            menu.add(0, VERB_DELETE_ID, adapterPosition, "Delete")


        }

    }
}