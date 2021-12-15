package com.saraha.paws.View.AnimalViews.AddEditAnimal.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.Animal
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.AnimalViews.AddEditAnimal.AddEditAnimalActivity
import com.saraha.paws.View.AnimalViews.AddEditAnimal.AddEditAnimalViewModel
import com.saraha.paws.databinding.FragmentAddEditAnimalPage2Binding


class AddEditAnimalPage2Fragment : Fragment() {

    private lateinit var viewModel: AddEditAnimalViewModel
    lateinit var binding: FragmentAddEditAnimalPage2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditAnimalPage2Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as AddEditAnimalActivity)[AddEditAnimalViewModel::class.java]

        //Get passed data from activity to set in fragment
        val animal = this.arguments?.getSerializable("animal")
        if (animal != null) setValues(animal as Animal)

        onFieldFocus()

        return binding.root
    }

    //Function to set User input when returning to fragment
    private fun setValues(animal: Animal) {
        if (animal.age.isNotEmpty()){  binding.edittextAddAnimalAge.setText(animal.age) }
        if (animal.color.isNotEmpty()){  binding.edittextAddAnimalColor.setText(animal.color) }
        if (animal.gender.isNotEmpty()){  binding.edittextAddAnimalGender.setText(animal.gender) }
        if (animal.personality.isNotEmpty()){  binding.edittextAddAnimalPersonality.setText(animal.personality) }
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.edittextAddAnimalAge.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateText(binding.edittextAddAnimalAge,0)
        }
        binding.edittextAddAnimalColor.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateText(binding.edittextAddAnimalColor,1)
        }
        binding.edittextAddAnimalGender.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateText(binding.edittextAddAnimalGender,2)
        }
        binding.edittextAddAnimalPersonality.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateText(binding.edittextAddAnimalPersonality,3)
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
                0 -> viewModel.setAnimalAge(v.text.toString())
                1 -> viewModel.setAnimalColor(v.text.toString())
                2 -> viewModel.setAnimalGender(v.text.toString())
                3 -> viewModel.setAnimalPersonality(v.text.toString())
                else -> return
            }
        }
    }
}