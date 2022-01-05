package com.saraha.paws.View.VendorsViews.ViewVendors

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.saraha.paws.Model.Vendor
import com.saraha.paws.databinding.FragmentViewVendorsBinding

class ViewVendorsFragment : Fragment() {

    //View model and binding lateinit property
    private val viewModel: ViewVendorsViewModel by viewModels()
    lateinit var binding: FragmentViewVendorsBinding

    //list of animals and recycler view adapter lateinit property
    lateinit var lists: List<Vendor>
    lateinit var adapter: VendorViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentViewVendorsBinding.inflate(inflater, container, false)

        val tag = arguments?.getString("tag")
        if (tag == "vets") getVetData() else if (tag == "stores") getStoreData()

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
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = VendorViewAdapter(this.requireContext(),list!!)
        recyclerView.adapter = adapter
    }




}