package com.saraha.paws.View.AddEditAnimal.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.saraha.paws.R
import com.saraha.paws.View.AddEditAnimal.AddEditAnimalActivity
import com.saraha.paws.View.AddEditAnimal.AddEditAnimalViewModel
import com.saraha.paws.databinding.FragmentAddEditAnimalPage1Binding
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

        return binding.root
    }
}