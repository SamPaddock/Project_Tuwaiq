package com.saraha.paws.View.CharityViews.ViewCharityDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Repository.FirebaseRepository

class ViewCharityDetailViewModel: ViewModel()  {

    //Variable to get liveData response from Firebase
    var deleteDocumentLiveData = MutableLiveData<Boolean>()

    //Function to handle firebase repository for deleting data
    fun deleteCharityFromFirebase(charityID: String){
        FirebaseRepository().deleteDocument("Charities", charityID).observeForever {
            deleteDocumentLiveData.postValue(it)
        }
    }

}