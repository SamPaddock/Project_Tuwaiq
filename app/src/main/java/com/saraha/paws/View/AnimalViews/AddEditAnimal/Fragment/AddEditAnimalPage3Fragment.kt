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
import com.saraha.paws.databinding.FragmentAddEditAnimalPage3Binding

class AddEditAnimalPage3Fragment : Fragment() {
    //View model and binding lateinit property
    private lateinit var viewModel: AddEditAnimalViewModel
    lateinit var binding: FragmentAddEditAnimalPage3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditAnimalPage3Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as AddEditAnimalActivity)[AddEditAnimalViewModel::class.java]

        //Get passed data from activity to set in fragment
        val animal = this.arguments?.getSerializable("animal")
        if (animal != null) setValues(animal as Animal)

        onFieldFocus()

        return binding.root
    }

    //Function to set User input when returning to fragment
    private fun setValues(animal: Animal) {
        if (animal.grooming.isNotEmpty()){  binding.edittextAddAnimalGrooming.setText(animal.grooming) }
        if (animal.medical.isNotEmpty()){  binding.edittextAddAnimalMedical.setText(animal.medical) }
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.edittextAddAnimalGrooming.addTextChangedListener {
            viewModel.validateText(binding.edittextAddAnimalGrooming,5)
        }
        binding.edittextAddAnimalMedical.addTextChangedListener {
            viewModel.validateText(binding.edittextAddAnimalMedical,6)
        }
    }


}