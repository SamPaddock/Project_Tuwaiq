package com.saraha.paws.View.AnimalViews.ViewAnimals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Animal
import com.saraha.paws.Repository.AnimalRepository

class ViewAnimalsViewModel: ViewModel() {

    var listOfAnimalsLiveData = MutableLiveData<List<Animal>>()

    fun getAllCharitiesFromFirebase(){
        AnimalRepository().getAllAnimals().observeForever {
            if (it.isNotEmpty()){ listOfAnimalsLiveData.postValue(it) }
        }
    }

}