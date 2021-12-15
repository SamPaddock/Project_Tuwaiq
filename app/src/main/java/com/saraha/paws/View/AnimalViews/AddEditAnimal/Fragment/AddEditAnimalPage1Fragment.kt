package com.saraha.paws.View.AnimalViews.AddEditAnimal.Fragment

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.Animal
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.AnimalViews.AddEditAnimal.AddEditAnimalActivity
import com.saraha.paws.View.AnimalViews.AddEditAnimal.AddEditAnimalViewModel
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

        return binding.root
    }

    private fun prepareDropdownMenus() {
        binding.edittextAddAnimalStatues.setAdapter(
            ArrayAdapter(context!!, R.layout.simple_list_item_1, viewModel.getStatusList()))
        binding.edittextAddAnimalType.setAdapter(
            ArrayAdapter(context!!, R.layout.simple_list_item_1, viewModel.getTypeList()))

    }

    fun selectPhoto(){
        binding.fabAddAnimalPhoto.setOnClickListener {
            val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(takePictureIntent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val imgData = data?.data!!
            binding.imageViewAnimalPhoto.setImageURI(imgData)
            viewModel.setAnimalPhoto(imgData)
        }
    }

    //Function to set User input when returning to fragment
    private fun setValues(animal: Animal) {
        if (animal.photoUrl.isNotEmpty()){ Picasso.get().load(animal.photoUrl).into(binding.imageViewAnimalPhoto)}
        if (animal.name.isNotEmpty()){  binding.edittextAddAnimalName.setText(animal.name) }
        if (animal.location.isNotEmpty()){  binding.editTextAddAnimalLocation.setText(animal.location) }
        if (animal.type.isNotEmpty()){  binding.edittextAddAnimalType.setText(animal.type) }
        if (animal.states.isNotEmpty()){  binding.edittextAddAnimalStatues.setText(animal.name) }
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.edittextAddAnimalName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateText(binding.edittextAddAnimalName,0)
        }
        binding.editTextAddAnimalLocation.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateText(binding.editTextAddAnimalLocation,1)
        }
        binding.edittextAddAnimalType.setOnItemClickListener { _, _, position, _ ->
            viewModel.setAnimalType(viewModel.getTypeList().get(position))
        }
        binding.edittextAddAnimalStatues.setOnItemClickListener { _, _, position, _ ->
            viewModel.setAnimalStatus(viewModel.getStatusList().get(position))
        }
    }

    private fun validateText(edittext: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().fieldVerification(edittext.text.toString())
        handleTextFields(edittext,result.string,index,isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
        } else {
            v.error = null
            when (index){
                0 -> viewModel.setAnimalName(v.text.toString())
                1 -> viewModel.setAnimalLocation(v.text.toString())
                else -> return
            }
        }
    }

}