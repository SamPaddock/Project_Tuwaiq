package com.saraha.paws.View.ViewAnimals

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.saraha.paws.R
import com.saraha.paws.View.AddEditAnimal.AddEditAnimalActivity
import com.saraha.paws.View.AddEditAnimal.AddEditAnimalViewModel
import com.saraha.paws.View.AddEditCharity.AddEditCharityActivity
import com.saraha.paws.View.AddEditCharity.AddEditCharityViewModel
import com.saraha.paws.databinding.FragmentAddEditAnimalPage1Binding
import com.saraha.paws.databinding.FragmentAddEditCharityPage1Binding
import com.saraha.paws.databinding.FragmentViewAnimalsBinding

class ViewAnimalsFragment : Fragment() {

    //private lateinit var viewModel: AddEditAnimalViewModel
    lateinit var binding: FragmentViewAnimalsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAnimalsBinding.inflate(inflater, container, false)

        //viewModel = ViewModelProvider(requireActivity() as AddEditAnimalActivity)[AddEditAnimalViewModel::class.java]

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this.context,AddEditAnimalActivity::class.java))
        }

        return binding.root
    }

}