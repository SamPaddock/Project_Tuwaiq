package com.saraha.paws.View.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.User
import com.saraha.paws.Repository.UserRepository

class HomeViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    val livedataUser = MutableLiveData<User>()

    //Function to handle firebase repository response to retrieving user data
    fun getUserDataFromFirebase(){
        UserRepository().getUserAccount().observeForever {
            if (it.email.isNotEmpty()){ livedataUser.postValue(it) }
        }
    }

}