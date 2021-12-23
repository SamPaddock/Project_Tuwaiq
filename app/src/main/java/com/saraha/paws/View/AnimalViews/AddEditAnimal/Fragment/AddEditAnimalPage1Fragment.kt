package com.saraha.paws.View.AnimalViews.AddEditAnimal.Fragment

import android.R
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.Animal
import com.saraha.paws.Util.Helper
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.Util.getStringAddress
import com.saraha.paws.Util.loadImage
import com.saraha.paws.View.AnimalViews.AddEditAnimal.AddEditAnimalActivity
import com.saraha.paws.View.AnimalViews.AddEditAnimal.AddEditAnimalViewModel
import com.saraha.paws.View.MapView.MapsActivity
import com.saraha.paws.databinding.FragmentAddEditAnimalPage1Binding
import com.squareup.picasso.Picasso

class AddEditAnimalPage1Fragment : Fragment() {

    private lateinit var viewModel: AddEditAnimalViewModel
    lateinit var binding: FragmentAddEditAnimalPage1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditAnimalPage1Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as AddEditAnimalActivity)[AddEditAnimalViewModel::class.java]

        //Get passed data from activity to set in fragment
        val animal = this.arguments?.getSerializable("animal")
        if (animal != null) setValues(animal as Animal)

        prepareDropdownMenus()

        onFieldFocus()

        selectPhoto()

        binding.imageViewAddLocation.setOnClickListener {
            val intent = Intent(this.requireContext(), MapsActivity::class.java)
            startActivityForResult(intent, 5)
        }

        return binding.root
    }

    //Function to set data in dropdown menus
    private fun prepareDropdownMenus() {
        binding.edittextAddAnimalStatues.setAdapter(
            ArrayAdapter(context!!, R.layout.simple_list_item_1, Helper().getStatusList()))
        binding.edittextAddAnimalType.setAdapter(
            ArrayAdapter(context!!, R.layout.simple_list_item_1, Helper().getTypeList()))

    }

    //Function to select a photo from device
    fun selectPhoto(){
        binding.fabAddAnimalPhoto.setOnClickListener {
            val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(takePictureIntent, 2)
        }
    }

    //Function to handle response from action result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val imgData = data?.data!!
            binding.imageViewAnimalPhoto.setImageURI(imgData)
            viewModel.setAnimalPhoto(imgData)
        }
        if (requestCode == 5 && resultCode == Activity.RESULT_OK){
            val lat = data?.getDoubleExtra("Lat", 0.0) ?: 0.0
            val lon = data?.getDoubleExtra("Lon", 0.0)  ?: 0.0

            val location = LatLng(lat, lon)
            binding.editTextAddAnimalLocation.setText(location.getStringAddress(this.requireContext()))
        }
    }

    //Function to set User input when returning to fragment
    private fun setValues(animal: Animal) {
        if (animal.photoUrl.isNotEmpty()){ binding.imageViewAnimalPhoto.loadImage(animal.photoUrl)}
        if (animal.name.isNotEmpty()){  binding.edittextAddAnimalName.setText(animal.name) }
        if (animal.type.isNotEmpty()){  binding.edittextAddAnimalType.setText(animal.type) }
        if (animal.states.isNotEmpty()){  binding.edittextAddAnimalStatues.setText(animal.states) }

        val location = LatLng(animal.latitude, animal.longitude)
        binding.editTextAddAnimalLocation.setText(location.getStringAddress(this.requireContext()))
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.edittextAddAnimalName.addTextChangedListener {
            viewModel.validateText( binding.edittextAddAnimalName,0)
        }
        binding.edittextAddAnimalType.setOnItemClickListener { _, _, position, _ ->
            viewModel.setAnimalType(Helper().getTypeList().get(position))
        }
        binding.edittextAddAnimalStatues.setOnItemClickListener { _, _, position, _ ->
            viewModel.setAnimalStatus(Helper().getStatusList().get(position))
        }
    }

}