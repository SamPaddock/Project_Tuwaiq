package com.saraha.paws.View.VendorsViews.ViewVendorDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saraha.paws.Model.Product
import com.saraha.paws.databinding.ListItemProductBinding

class ProductViewAdapter(var context: Context, var data: List<Product>) :
    RecyclerView.Adapter<ProductViewHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ListItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.itemView.setOnClickListener {}
    }
}

class ProductViewHolder(val binding: ListItemProductBinding) : RecyclerView.ViewHolder(binding.root)