package com.saraha.paws.View.VendorsViews.ViewVendorDetails

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.saraha.paws.Model.Charity
import com.saraha.paws.Model.Product
import com.saraha.paws.Model.Vendor
import com.saraha.paws.R
import com.saraha.paws.Util.Helper
import com.saraha.paws.Util.getStringAddress
import com.saraha.paws.Util.loadImage
import com.saraha.paws.Util.toast
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
            setupToolbar(vendor)
            //getVetData(vendor)
        }

        setContentView(binding.root)
    }

    //Function to set toolbar and back button
    private fun setupToolbar(vendor: Vendor) {
        val mainToolbar = binding.toolbarViewVendor
        mainToolbar.title = vendor.name
        mainToolbar.setNavigationIcon(R.drawable.ic_back_24)
        setSupportActionBar(mainToolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun setValues(vendor: Vendor) {
        binding.imageViewDisplayVendorPhoto.loadImage(vendor.photo)
        binding.textViewDisplayVendorAbout.setText(vendor.about)
        val latLon = LatLng(vendor.latitude, vendor.longitude)
        binding.textViewDisplayVendorBranch.setText(latLon.getStringAddress(this))
        binding.imageViewDisplayVendorLink.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(vendor.link)))
        }
        binding.imageViewDisplayVendorWhatsapp.setOnClickListener {
            openWhatsapp(vendor.phone)
        }
        binding.imageViewDisplayVendorEmail.setOnClickListener {
            openMail(vendor.email)
        }
        binding.imageViewDisplayVendorLocation.setOnClickListener {
            openGoogleMaps(vendor.name, LatLng(vendor.latitude,vendor.longitude))

        }
    }

    //Function to handle to mail icon click
    private fun openMail(email: String){
        // create an Intent to send data to mail
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject_temp))
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_intro_temp))
        startActivity(intent)
    }

    //Function to handle to whatsapp icon click
    private fun openWhatsapp(mobile: String){
        // create an Intent to send data to the whatsapp
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")
        if (intent.resolveActivity(packageManager) != null) {
            val url = "https://api.whatsapp.com/send?phone=+$mobile"
            intent.data = Uri.parse(url)
            startActivity(intent)
        }else {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp"))
            startActivity(i)
        }
    }

    fun getVetData(vendor: Vendor){
        viewModel.getAllProductsFromFirebase(vendor.type, vendor.vid)
        viewModel.listOfProductsLiveData.observe(this){
            setRecyclerViewWithData(it)
        }
    }

    private fun openGoogleMaps(name: String, location: LatLng){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("http://maps.google.com/maps?q=loc:"+location.latitude+","
                +location.longitude+"("+name+")")
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(packageManager) != null){
            startActivity(intent)
        } else {
            this.toast(getString(R.string.location_error))
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