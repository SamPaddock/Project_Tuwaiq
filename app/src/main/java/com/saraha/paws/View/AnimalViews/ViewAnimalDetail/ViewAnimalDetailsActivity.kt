package com.saraha.paws.View.AnimalViews.ViewAnimalDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.google.android.gms.maps.model.LatLng
import com.saraha.paws.Model.Animal
import com.saraha.paws.R
import com.saraha.paws.Util.AppSharedPreference
import com.saraha.paws.Util.getStringAddress
import com.saraha.paws.Util.loadImage
import com.saraha.paws.Util.toast
import com.saraha.paws.View.AnimalViews.AddEditAnimal.AddEditAnimalActivity
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityActivity
import com.saraha.paws.databinding.ActivityViewAnimalDetailsBinding
import com.squareup.picasso.Picasso

class ViewAnimalDetailsActivity : AppCompatActivity() {

    val viewModel: ViewAnimalDetailsViewModel by viewModels()
    lateinit var binding: ActivityViewAnimalDetailsBinding
    lateinit var animal: Animal

    val sharedPref = AppSharedPreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAnimalDetailsBinding.inflate(layoutInflater)

        val data = intent.getSerializableExtra("animal")

        if (data != null){
            animal = data as Animal
            setValues(animal)
            setupToolbar()
        } else {
            this.toast(getString(R.string.view_error_general))
            finish()
        }

        setContentView(binding.root)
    }

    private fun setupToolbar() {
        val mainToolbar = binding.toolbarViewAnimal
        mainToolbar.title = animal.name
        mainToolbar.setNavigationIcon(R.drawable.ic_back_24)
        setSupportActionBar(mainToolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (sharedPref.read("tName","")!! == "Admin"){
            menuInflater.inflate(R.menu.edit_delete_menu_items,menu)
            menu?.findItem(R.id.edit_item_1)?.setOnMenuItemClickListener {
                redirectToEditContent()
                true
            }
            menu?.findItem(R.id.delete_item_2)?.setOnMenuItemClickListener {
                deleteAnimal()
                true
            }
        } else {
            menuInflater.inflate(R.menu.edit_menu_item,menu)
            menu?.findItem(R.id.edit_item)?.setOnMenuItemClickListener {
                redirectToEditContent()
                true
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun deleteAnimal() {
        viewModel.deleteCharityFromFirebase(animal.aid!!)
        viewModel.deleteDocumentLiveData.observe(this){
            if (it) {
                this.toast(getString(R.string.successful_delete_animal))
                finish()
            } else {
                this.toast(getString(R.string.failure_delete_animal))
            }
        }
    }

    private fun redirectToEditContent() {
        val intent = Intent(this, AddEditAnimalActivity::class.java)
        intent.putExtra("type", "Edit")
        intent.putExtra("animal", animal)
        startActivity(intent)
    }

    //Function to set data in textviews
    private fun setValues(animal: Animal) {
        //set main values
        binding.textViewDisplayAnimalName.setText(animal.name)
        binding.textViewDisplayAnimalVolunteer.setText(animal.volunteerName)
        binding.textViewDisplayAnimalCharity.setText(animal.groupName)
        //set image
        binding.imageViewDisplayAnimalPhoto.loadImage(animal.photoUrl)
        //set secondary values
        binding.textViewDisplayAnimalType.setText(animal.type)
        binding.textViewDisplayAnimalGender.setText(animal.gender)
        binding.textViewDisplayAnimalAge.setText(animal.age)
        binding.textViewDisplayAnimalColor.setText(animal.color)
        val location = LatLng(animal.latitude, animal.longitude)
        binding.textViewDisplayAnimalLocation.setText(location.getStringAddress(this))
        binding.textViewDisplayAnimalPersonality.setText(animal.personality)
        binding.textViewDisplayAnimalGrooming.setText(animal.grooming)
        binding.textViewDisplayAnimalMedical.setText(animal.medical)
    }


}