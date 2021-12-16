package com.saraha.paws.View.AccountViews.Profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.User
import com.saraha.paws.Repository.UserRepository

class ProfileViewModel: ViewModel() {

    val livedataUser = MutableLiveData<User>()

    fun getUserDataFromFirebase(){
        UserRepository().getUserAccount().observeForever {
            if (it.email.isNotEmpty()){ livedataUser.postValue(it) }
        }
    }

}