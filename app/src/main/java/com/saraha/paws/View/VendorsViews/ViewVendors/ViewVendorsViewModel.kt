package com.saraha.paws.View.VendorsViews.ViewVendors

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Vendor
import com.saraha.paws.Repository.StoreRepository
import com.saraha.paws.Repository.VetRepository

class ViewVendorsViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    var listOfVendorsLiveData = MutableLiveData<List<Vendor>>()

    //Function to handle firebase repository for retrieving all data for vets
    fun getAllVetsFromFirebase(){
        VetRepository().getAll().observeForever {
            if (it.first?.isNotEmpty() == true){ listOfVendorsLiveData.postValue(it.first!!) }
        }
    }

    //Function to handle firebase repository for retrieving all data for stores
    fun getAllStoresFromFirebase(){
        StoreRepository().getAll().observeForever {
            if (it.first?.isNotEmpty() == true){ listOfVendorsLiveData.postValue(it.first!!) }
        }
    }
}