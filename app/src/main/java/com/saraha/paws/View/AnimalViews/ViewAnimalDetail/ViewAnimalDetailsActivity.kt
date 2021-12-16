package com.saraha.paws.View.AnimalViews.ViewAnimalDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saraha.paws.Model.Charity
import com.saraha.paws.R
import com.saraha.paws.databinding.ActivityViewCharityDetailBinding

class ViewAnimalDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewCharityDetailBinding
    lateinit var animal: Animal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_animal_details)
    }
}