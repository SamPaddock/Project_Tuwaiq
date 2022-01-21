package com.saraha.paws.View.VendorsViews.ViewVendors

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.saraha.paws.Model.Vendor
import com.saraha.paws.Util.getCurrentLocation
import com.saraha.paws.Util.loadImage
import com.saraha.paws.View.VendorsViews.ViewVendorDetails.ViewVendorDetailsActivity
import com.saraha.paws.databinding.ListItemVendorBinding

class VendorViewAdapter(var context: Context, var data: List<Vendor>)
    : RecyclerView.Adapter<VendorViewHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        val binding = ListItemVendorBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return VendorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        holder.binding.textViewVenderTitle.setText(data[position].name)
        holder.binding.imageViewVendorPhoto.loadImage(data[position].photo)

        val latLon = LatLng(data[position].latitude, data[position].longitude)
        latLon.getCurrentLocation(context){
            val distance = if (it < 1000) {
                val roundedDistance = String.format("%.2f", it).toDouble()
                "$roundedDistance m"
            }
            else if (it < 100000) {
                val roundedDistance = String.format("%.2f", it/1000).toDouble()
                "$roundedDistance km"
            }
            else ""
            holder.binding.textViewVendurDistance.setText(distance)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ViewVendorDetailsActivity::class.java)
            intent.putExtra("vendor", data[position])
            context.startActivity(intent)
        }
    }
}

class VendorViewHolder(val binding: ListItemVendorBinding) : RecyclerView.ViewHolder(binding.root)