package com.saraha.paws.View.ViewCharities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saraha.paws.Model.Charity
import com.saraha.paws.View.ViewCharityDetail.ViewCharityDetailActivity
import com.saraha.paws.databinding.ListItemCharitiesBinding
import com.squareup.picasso.Picasso

class CharityViewAdapter(var context: Context, var data: List<Charity>) :
    RecyclerView.Adapter<CharityViewHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharityViewHolder {
        val binding =
            ListItemCharitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharityViewHolder, position: Int) {
        Picasso.get().load(Uri.parse(data[position].photo))
            .into(holder.binding.imageViewListCharityPhoto)
        holder.binding.textViewCharityName.setText(data[position].name)
        holder.binding.textViewCharityEmail.setText(data[position].email)
        holder.binding.textViewCharityMobile.setText(data[position].mobile)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ViewCharityDetailActivity::class.java)
            intent.putExtra("charity", data[position])
            context.startActivity(intent)
        }
    }
}

class CharityViewHolder(val binding: ListItemCharitiesBinding) :
    RecyclerView.ViewHolder(binding.root)