package com.saraha.paws.View.ShowFacts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.saraha.paws.Util.*
import androidx.recyclerview.widget.RecyclerView
import com.saraha.paws.Model.Facts.CatFacts
import com.saraha.paws.databinding.ListItemFactsBinding

class DisplayFactsViewAdapter(var context: Context, var data: CatFacts) :
    RecyclerView.Adapter<DisplayFactsViewHolder>() {

    override fun getItemCount() = data.data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayFactsViewHolder {
        val binding = ListItemFactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DisplayFactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DisplayFactsViewHolder, position: Int) {
        holder.binding.textViewFact.setText(data.data[position].fact.addQuote())
    }
}

class DisplayFactsViewHolder(val binding: ListItemFactsBinding) : RecyclerView.ViewHolder(binding.root)