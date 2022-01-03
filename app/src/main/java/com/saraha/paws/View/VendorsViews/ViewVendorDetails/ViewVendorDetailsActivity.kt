package com.saraha.paws.View.VendorsViews.ViewVendorDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saraha.paws.databinding.ActivityViewVendorDetailsBinding

class ViewVendorDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewVendorDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewVendorDetailsBinding.inflate(layoutInflater)



        setContentView(binding.root)
    }
}