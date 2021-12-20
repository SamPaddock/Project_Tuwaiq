package com.saraha.paws.View.AnimalViews.ViewAnimalDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Animal
import com.saraha.paws.Repository.AnimalRepository
import com.saraha.paws.Repository.FirebaseRepository

class ViewAnimalDetailsViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    var deleteDocumentLiveData = MutableLiveData<Boolean>()

    //Function to handle firebase repository for deleting data
    fun deleteCharityFromFirebase(animalID: String){
        FirebaseRepository().deleteDocument("Animals", animalID).observeForever {
            deleteDocumentLiveData.postValue(it)
        }
    }

}