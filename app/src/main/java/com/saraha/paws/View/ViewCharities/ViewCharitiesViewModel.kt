package com.saraha.paws.View.ViewCharities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Charity
import com.saraha.paws.Repository.CharityRepository

class ViewCharitiesViewModel: ViewModel() {

    var listOfCharitiesLiveData = MutableLiveData<List<Charity>>()

    fun getAllCharitiesFromFirebase(){
        CharityRepository().getAllCharities().observeForever {
            if (it.isNotEmpty()){
                listOfCharitiesLiveData.postValue(it)
            }
        }
    }


}