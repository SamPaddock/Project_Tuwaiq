package com.saraha.paws.View.VendorsViews.ViewVendorDetails

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Product
import com.saraha.paws.Repository.ProductRepository

class ViewVendorDetailsViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    var listOfProductsLiveData = MutableLiveData<List<Product>>()

    //Function to handle firebase repository for retrieving all data for vets
    fun getAllProductsFromFirebase(collection: String, vendorID: String){
        ProductRepository().getAll(collection, vendorID).observeForever {
            Log.d(TAG,"ViewVendorDetailsViewModel: - getAllProductsFromFirebase: - : ${it.first}")
            if (it.first?.isNotEmpty() == true){ listOfProductsLiveData.postValue(it.first!!) }
        }
    }
}