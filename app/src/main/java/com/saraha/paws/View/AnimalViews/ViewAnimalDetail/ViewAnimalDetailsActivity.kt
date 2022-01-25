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
import com.saraha.paws.databinding.ActivityViewAnimalDetailsBinding

class ViewAnimalDetailsActivity : AppCompatActivity() {
    //View model and binding lateinit property
    val viewModel: ViewAnimalDetailsViewModel by viewModels()
    lateinit var binding: ActivityViewAnimalDetailsBinding
    lateinit var animal: Animal
    //Shared preference helper class object
    val sharedPref = AppSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAnimalDetailsBinding.inflate(layoutInflater)
        //send data from intent, if not null, then display content and set toolbar
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
    //Function to set toolbar and back button
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
    //set up item menu depending on use type with on item click listener
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (sharedPref.read("tName","")!! == "Admin"){
            menuInflater.inflate(R.menu.edit_delete_menu_items,menu)
            val editItem = menu?.findItem(R.id.edit_item)
            editItem?.setOnMenuItemClickListener { redirectToEditContent(); true }
            val deleteItem = menu?.findItem(R.id.delete_item)
            deleteItem?.setOnMenuItemClickListener { deleteAnimal(); true}
        } else {
            menuInflater.inflate(R.menu.edit_menu_item,menu)
            val editItem = menu?.findItem(R.id.edit_item_1)
            editItem?.setOnMenuItemClickListener { redirectToEditContent(); true }
        }
        return super.onCreateOptionsMenu(menu)
    }

    //Function to handle response from action result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9 && resultCode == RESULT_OK){
            animal = data?.getSerializableExtra("animal") as Animal
            setValues(animal)
        }
    }
    //Function to delete an animal from firebase
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

    //Function to start Add/Edit activity with result on completion
    private fun redirectToEditContent() {
        val intent = Intent(this, AddEditAnimalActivity::class.java)
        intent.putExtra("type", "Edit")
        intent.putExtra("animal", animal)
        startActivityForResult(intent, 9)
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