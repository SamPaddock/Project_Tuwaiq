package com.saraha.paws.View.VendorsViews.ViewVendors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.saraha.paws.Model.Animal
import com.saraha.paws.Model.Vendor
import com.saraha.paws.R
import com.saraha.paws.View.AnimalViews.ViewAnimals.AnimalViewAdapter
import com.saraha.paws.View.AnimalViews.ViewAnimals.ViewAnimalsViewModel
import com.saraha.paws.databinding.FragmentViewAnimalsBinding
import com.saraha.paws.databinding.FragmentViewVendorsBinding

class ViewVendorsFragment : Fragment() {

    //View model and binding lateinit property
    private lateinit var viewModel: ViewVendorsViewModel
    lateinit var binding: FragmentViewVendorsBinding

    //list of animals and recycler view adapter lateinit property
    lateinit var lists: List<Vendor>
    lateinit var adapter: VendorViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentViewVendorsBinding.inflate(inflater, container, false)

        val tag = arguments?.getString("tag")
        if (tag == "vet") getVetData()
        else if (tag == "store") getStoreData()

        return binding.root
    }

    fun getVetData(){
        viewModel.getAllVetsFromFirebase()
        viewModel.listOfVendorsLiveData.observe(this){
            setRecyclerViewWithData(it)
        }
    }

    fun getStoreData(){
        viewModel.getAllStoresFromFirebase()
        viewModel.listOfVendorsLiveData.observe(this){
            setRecyclerViewWithData(it)
        }
    }

    //Function to set data into recyclerview
    private fun  setRecyclerViewWithData(list: List<Vendor>?) {
        val recyclerView = binding.recyclerViewvendor
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        GridLayoutManager(this.context,2)
        adapter = VendorViewAdapter(this.requireContext(),list!!)
        recyclerView.adapter = adapter
    }




}