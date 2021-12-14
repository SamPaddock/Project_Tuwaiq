package com.saraha.paws.View.ViewAnimals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Animal
import com.saraha.paws.Model.Charity
import com.saraha.paws.Repository.AnimalRepository
import com.saraha.paws.Repository.CharityRepository

class ViewAnimalsViewModel: ViewModel() {

    var listOfAnimalsLiveData = MutableLiveData<List<Animal>>()

    fun getAllCharitiesFromFirebase(){
        AnimalRepository().getAllAnimals().observeForever {
            if (it.isNotEmpty()){
                listOfAnimalsLiveData.postValue(it)
            }
        }
    }

}