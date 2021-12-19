package com.saraha.paws.View.AnimalViews.ViewAnimalDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.saraha.paws.Model.Animal
import com.saraha.paws.R
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityActivity
import com.saraha.paws.databinding.ActivityViewAnimalDetailsBinding
import com.squareup.picasso.Picasso

class ViewAnimalDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewAnimalDetailsBinding
    lateinit var animal: Animal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAnimalDetailsBinding.inflate(layoutInflater)

        setupToolbar()

        val data = intent.getSerializableExtra("animal")

        if (data != null){
            animal = data as Animal
            setValues(animal)
        }

        setContentView(binding.root)
    }

    private fun setupToolbar() {
        val mainToolbar = binding.toolbarViewAnimal
        mainToolbar.setNavigationIcon(R.drawable.ic_back_24)
        setSupportActionBar(mainToolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu_item,menu)
        menu?.findItem(R.id.edit_item)?.setOnMenuItemClickListener {
            val intent = Intent(this, AddEditCharityActivity::class.java)
            intent.putExtra("type", "Edit")
            intent.putExtra("animal", animal)
            startActivity(intent)
            true
        }
        return super.onCreateOptionsMenu(menu)
    }

    //Function to set data in textviews
    private fun setValues(animal: Animal) {
        //set main values
        binding.textViewDisplayAnimalName.setText(animal.name)
        binding.textViewDisplayAnimalVolunteer.setText(animal.volunteerName)
        binding.textViewDisplayAnimalCharity.setText(animal.groupName)
        //set image
        Picasso.get().load(animal.photoUrl).into(binding.imageViewDisplayAnimalPhoto)
        //set secondary values
        binding.textViewDisplayAnimalType.setText(animal.type)
        binding.textViewDisplayAnimalGender.setText(animal.gender)
        binding.textViewDisplayAnimalAge.setText(animal.age)
        binding.textViewDisplayAnimalColor.setText(animal.color)
        binding.textViewDisplayAnimalPersonality.setText(animal.personality)
        binding.textViewDisplayAnimalGrooming.setText(animal.grooming)
        binding.textViewDisplayAnimalMedical.setText(animal.medical)
    }


}