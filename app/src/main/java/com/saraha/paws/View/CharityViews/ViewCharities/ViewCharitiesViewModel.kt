package com.saraha.paws.View.CharityViews.ViewCharities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Charity
import com.saraha.paws.Repository.CharityRepository

class ViewCharitiesViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    var listOfCharitiesLiveData = MutableLiveData<List<Charity>>()

    //Function to handle firebase repository for retrieving all data for charities
    fun getAllCharitiesFromFirebase(){
        CharityRepository().getAll().observeForever {
            if (it.isNotEmpty()){ listOfCharitiesLiveData.postValue(it) }
        }
    }

}