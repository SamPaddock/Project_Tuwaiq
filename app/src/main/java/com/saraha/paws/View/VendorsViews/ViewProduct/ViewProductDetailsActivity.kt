package com.saraha.paws.View.VendorsViews.ViewProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saraha.paws.databinding.ActivityViewProductDetailsBinding

class ViewProductDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewProductDetailsBinding.inflate(layoutInflater)



        setContentView(binding.root)
    }
}