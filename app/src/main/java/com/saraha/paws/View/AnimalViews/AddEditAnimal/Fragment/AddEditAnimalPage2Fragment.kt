package com.saraha.paws.View.AnimalViews.AddEditAnimal.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
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
    ): View {
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
        binding.edittextAddAnimalAge.addTextChangedListener {
            viewModel.validateText(binding.edittextAddAnimalAge,1)
        }
        binding.edittextAddAnimalColor.addTextChangedListener {
            viewModel.validateText(binding.edittextAddAnimalColor,2)
        }
        binding.edittextAddAnimalGender.addTextChangedListener {
            viewModel.validateText(binding.edittextAddAnimalGender,3)
        }
        binding.edittextAddAnimalPersonality.addTextChangedListener {
            viewModel.validateText(binding.edittextAddAnimalPersonality,4)
        }
    }
}