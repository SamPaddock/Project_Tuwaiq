package com.saraha.paws.View.VendorsViews.ViewVendorDetails

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.saraha.paws.Model.Product
import com.saraha.paws.Model.Vendor
import com.saraha.paws.Util.getStringAddress
import com.saraha.paws.Util.loadImage
import com.saraha.paws.databinding.ActivityViewVendorDetailsBinding

class ViewVendorDetailsActivity : AppCompatActivity() {
    //View model and binding lateinit property
    private val viewModel: ViewVendorDetailsViewModel by viewModels()
    lateinit var binding: ActivityViewVendorDetailsBinding

    //list of products and recycler view adapter lateinit property
    lateinit var lists: List<Product>
    lateinit var adapter: ProductViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewVendorDetailsBinding.inflate(layoutInflater)

        val vendor = intent.getSerializableExtra("vendor")
        if (vendor != null){
            setValues(vendor as Vendor)
            //getVetData(vendor)
        }

        setContentView(binding.root)
    }

    fun setValues(vendor: Vendor) {
        binding.imageViewDisplayVendorPhoto.loadImage(vendor.photo)
        binding.textViewDisplayVendorAbout.setText(vendor.about)
        val latLon = LatLng(vendor.latitude, vendor.longitude)
        binding.textViewDisplayVendorBranch.setText(latLon.getStringAddress(this))
    }

    fun getVetData(vendor: Vendor){
        viewModel.getAllProductsFromFirebase(vendor.type, vendor.vid)
        viewModel.listOfProductsLiveData.observe(this){
            setRecyclerViewWithData(it)
        }
    }

    //Function to set data into recyclerview
    private fun  setRecyclerViewWithData(list: List<Product>) {
        val recyclerView = binding.recyclerViewProducts
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = ProductViewAdapter(this,list)
        recyclerView.adapter = adapter
    }
}