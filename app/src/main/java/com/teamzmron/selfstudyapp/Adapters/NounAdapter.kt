package com.teamzmron.selfstudyapp.Adapters

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.NOUN_DELETE_ID
import com.teamzmron.selfstudyapp.Helper.Constants.Companion.NOUN_EDIT_ID
import com.teamzmron.selfstudyapp.R
import com.teamzmron.selfstudyapp.Room.Entity.Noun

class NounAdapter : RecyclerView.Adapter<NounAdapter.WordsViewHolder>() {

    private var context: Context? = null
    private var nounList: ArrayList<Noun> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        return WordsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_noun_items, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return nounList.size
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.etEnglish!!.text = nounList[position].english

    }

    fun setNouns(nouns: List<Noun>) {
        this.nounList.clear()
        this.nounList.addAll(nouns)
        notifyDataSetChanged()
    }

    fun getNoun(position: Int) : Noun {
        return nounList[position]
    }

    fun setContext(context: Context) {
        this.context = context
    }




    inner class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
   View.OnCreateContextMenuListener {
        var etEnglish: TextView? = null
        var cvParent: CardView? = null

        init {
            Log.v("ViewHolder", "Viewholder")
            etEnglish = itemView.findViewById(R.id.wordlist_english)
            cvParent = itemView.findViewById(R.id.cardView_wordlist_parent)
            itemView.setOnCreateContextMenuListener(this)

        }


        override fun onCreateContextMenu(
            menu: ContextMenu?,
            p1: View?,
            p2: ContextMenu.ContextMenuInfo?
        ) {

            menu!!.add(0, NOUN_EDIT_ID, adapterPosition, "Edit")
            menu.add(0, NOUN_DELETE_ID, adapterPosition, "Delete")


        }

    }

}