package com.saraha.paws.View.AnimalViews.ViewAnimals

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.saraha.paws.Model.Animal
import com.saraha.paws.View.AnimalViews.ViewAnimalDetail.ViewAnimalDetailsActivity
import com.saraha.paws.databinding.ListItemAnimalsBinding
import com.squareup.picasso.Picasso

class AnimalViewAdapter(var context: Context, var data: List<Animal>) :
    RecyclerView.Adapter<AnimalViewHolder>(), Filterable {

    var animalFilterList = data

    override fun getItemCount() = animalFilterList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding =
            ListItemAnimalsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        Picasso.get().load(Uri.parse(animalFilterList[position].photoUrl))
            .into(holder.binding.imageViewAnimalPhoto)
        holder.binding.textViewAnimalName.setText(animalFilterList[position].name)
        holder.binding.textViewAnimalGender.setText(animalFilterList[position].gender)
        holder.binding.textViewAnimalAge.setText(animalFilterList[position].age)
        holder.binding.chipAnimalStatues.setText(animalFilterList[position].states)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ViewAnimalDetailsActivity::class.java)
            intent.putExtra("animal", animalFilterList[position])
            context.startActivity(intent)
        }
    }

    //Filter function to handle recycler view filter
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    animalFilterList = data as ArrayList<Animal>
                } else {
                    val resultList = ArrayList<Animal>()
                    for (row in animalFilterList) {
                        if (row.type.lowercase().contains(constraint.toString().lowercase())){
                            resultList.add(row)
                        } else if (row.states.lowercase().contains(constraint.toString().lowercase())){
                            resultList.add(row)
                        }
                    }
                    animalFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = animalFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                animalFilterList = results?.values as ArrayList<Animal>
                notifyDataSetChanged()
            }
        }
    }
}

class AnimalViewHolder(val binding: ListItemAnimalsBinding) : RecyclerView.ViewHolder(binding.root)