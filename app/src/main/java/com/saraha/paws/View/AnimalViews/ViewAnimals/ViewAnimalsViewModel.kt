package com.saraha.paws.View.AnimalViews.ViewAnimals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Animal
import com.saraha.paws.Repository.AnimalRepository

class ViewAnimalsViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    var listOfAnimalsLiveData = MutableLiveData<List<Animal>>()

    //Function to handle firebase repository for retrieving all data for charities
    fun getAllAnimalsFromFirebase(){
        AnimalRepository().getAll().observeForever {
            if (it.isNotEmpty()){ listOfAnimalsLiveData.postValue(it) }
        }
    }

}