package com.saraha.paws.View.AnimalViews.ViewAnimals

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.saraha.paws.Model.Animal
import com.saraha.paws.R
import com.saraha.paws.Util.Helper
import com.saraha.paws.View.AnimalViews.AddEditAnimal.AddEditAnimalActivity
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.databinding.FragmentViewAnimalsBinding

class ViewAnimalsFragment : Fragment() {

    private lateinit var viewModel: ViewAnimalsViewModel
    lateinit var binding: FragmentViewAnimalsBinding

    lateinit var animal: List<Animal>
    lateinit var adapter: AnimalViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewAnimalsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[ViewAnimalsViewModel::class.java]

        setHasOptionsMenu(true)

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this.context, AddEditAnimalActivity::class.java)
            intent.putExtra("type", "Add")
            startActivity(intent)
        }

        //Set filter textField and onClick listener
        setRecyclerViewFilter()
        onClickFilterListener()

        return binding.root
    }

    override fun onResume() {
        getAllAnimals()
        super.onResume()
    }

    //Function for an toolbar content and handler
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recycler_view_menu,menu)
        menu.findItem(R.id.filter_item_menu)?.setOnMenuItemClickListener {
            var isFilterVisible = binding.constraintLayoutFilterContent.visibility
            if (isFilterVisible == View.VISIBLE) {
                binding.constraintLayoutFilterContent.visibility = View.GONE
            } else {
                binding.constraintLayoutFilterContent.visibility = View.VISIBLE
            }
            true
        }
        super.onCreateOptionsMenu(menu,inflater)
    }

    //Function to get all animals from Firestore
    private fun getAllAnimals(){
        viewModel.getAllAnimalsFromFirebase()
        viewModel.listOfAnimalsLiveData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                animal = it
                setRecyclerViewWithData(animal)
            }
        }
    }

    //Function to set data into recyclerview
    private fun setRecyclerViewWithData(animals: List<Animal>?) {
        val recyclerView = binding.recyclerViewAnimalList
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            GridLayoutManager(this.context,2)
        adapter = AnimalViewAdapter(this.context!!,animals!!)
        recyclerView.adapter = adapter
    }

    private fun setRecyclerViewFilter(){
        binding.inputTextFilterStatus.setAdapter(
            ArrayAdapter(context!!, android.R.layout.simple_list_item_1, Helper().getStatusList()))
        binding.inputTextFilterType.setAdapter(
            ArrayAdapter(context!!, android.R.layout.simple_list_item_1, Helper().getTypeList()))
    }

    private fun onClickFilterListener(){
        binding.inputTextFilterType.setOnItemClickListener { _, _, position, _ ->
            Log.d(TAG,"ViewAnimalsFragment: - onClickFilterListener: - getTypeList: ${Helper().getTypeList().get(position)}")
            val selectedType = Helper().getTypeList().get(position)
            adapter.filter.filter(selectedType)
        }
        binding.inputTextFilterStatus.setOnItemClickListener { _, _, position, _ ->
            Log.d(TAG,"ViewAnimalsFragment: - onClickFilterListener: - getStatusList: ${Helper().getStatusList().get(position)}")
            val selectedStatus = Helper().getStatusList().get(position)
            adapter.filter.filter(selectedStatus)
        }
    }

}