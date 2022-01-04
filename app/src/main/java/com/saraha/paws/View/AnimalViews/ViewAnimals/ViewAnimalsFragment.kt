package com.saraha.paws.View.AnimalViews.ViewAnimals

import android.content.Intent
import android.os.Bundle
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
import com.saraha.paws.View.Home.Home.HomeActivity
import com.saraha.paws.databinding.FragmentViewAnimalsBinding

class ViewAnimalsFragment : Fragment() {
    //View model and binding lateinit property
    private lateinit var viewModel: ViewAnimalsViewModel
    lateinit var binding: FragmentViewAnimalsBinding
    //list of animals and recycler view adapter lateinit property
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
        if (arguments?.getString("tag") == null && arguments?.getString("tag") != "adoption"){
            inflater.inflate(R.menu.recycler_view_menu,menu)
            menu.findItem(R.id.filter_item_menu)?.setOnMenuItemClickListener {
                val isFilterVisible = binding.constraintLayoutFilterContent.visibility
                if (isFilterVisible == View.VISIBLE) {
                    binding.constraintLayoutFilterContent.visibility = View.GONE
                } else {
                    binding.constraintLayoutFilterContent.visibility = View.VISIBLE
                }
                true
            }
        }
        super.onCreateOptionsMenu(menu,inflater)
    }

    //Function to get all animals from Firestore
    private fun getAllAnimals(){
        viewModel.getAllAnimalsFromFirebase()
        viewModel.listOfAnimalsLiveData.observe(viewLifecycleOwner){ list ->
            if (list.isNotEmpty()){
                if (arguments?.getString("tag") != null){
                    animal = list.filter { it.states == "For Adoption"}
                } else {
                    animal = list
                }

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

    //Function to set data into recyclerview
    private fun setRecyclerViewFilter(){
        binding.inputTextFilterStatus.setAdapter(
            ArrayAdapter(context!!, android.R.layout.simple_list_item_1, Helper().getStatusList()))
        binding.inputTextFilterType.setAdapter(
            ArrayAdapter(context!!, android.R.layout.simple_list_item_1, Helper().getTypeList()))
    }

    //Function to handle filter list dropdown menu listener
    private fun onClickFilterListener(){
        binding.inputTextFilterType.setOnItemClickListener { _, _, position, _ ->
            val selectedType = Helper().getTypeList().get(position)
            adapter.filter.filter(selectedType)
        }
        binding.inputTextFilterStatus.setOnItemClickListener { _, _, position, _ ->
            val selectedStatus = Helper().getStatusList().get(position)
            adapter.filter.filter(selectedStatus)
        }
    }

}