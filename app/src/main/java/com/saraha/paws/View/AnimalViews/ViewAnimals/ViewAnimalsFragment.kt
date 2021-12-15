package com.saraha.paws.View.AnimalViews.ViewAnimals

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.saraha.paws.Model.Animal
import com.saraha.paws.View.AnimalViews.AddEditAnimal.AddEditAnimalActivity
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.databinding.FragmentViewAnimalsBinding

class ViewAnimalsFragment : Fragment() {

    private lateinit var viewModel: ViewAnimalsViewModel
    lateinit var binding: FragmentViewAnimalsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAnimalsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[ViewAnimalsViewModel::class.java]

        getAllAnimals()

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this.context, AddEditAnimalActivity::class.java)
            intent.putExtra("type", "Add")
            startActivity(intent)
        }

        return binding.root
    }

    private fun getAllAnimals(){
        viewModel.getAllCharitiesFromFirebase()
        viewModel.listOfAnimalsLiveData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                setRecyclerViewWithData(it)
            }
        }
    }

    private fun setRecyclerViewWithData(Animals: List<Animal>?) {
        val recyclerView = binding.recyclerViewAnimalList
        recyclerView.layoutManager = GridLayoutManager(this.context,2)
        recyclerView.adapter = AnimalViewAdapter(this.context!!,Animals!!)
    }

}