package com.saraha.paws.View.VendorsViews.ViewVendors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saraha.paws.Model.Vendor
import com.saraha.paws.databinding.ListItemVendorBinding

class VendorViewAdapter(var context: Context, var data: List<Vendor>) :
    RecyclerView.Adapter<VendorViewHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        val binding =
            ListItemVendorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VendorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        holder.itemView.setOnClickListener {}
    }
}

class VendorViewHolder(val binding: ListItemVendorBinding) : RecyclerView.ViewHolder(binding.root)