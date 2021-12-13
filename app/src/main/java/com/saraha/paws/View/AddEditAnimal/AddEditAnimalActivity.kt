package com.saraha.paws.View.AddEditAnimal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saraha.paws.R
import com.saraha.paws.databinding.ActivityAddEditAnimalBinding

class AddEditAnimalActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddEditAnimalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditAnimalBinding.inflate(layoutInflater)




        setContentView(binding.root)
    }
}