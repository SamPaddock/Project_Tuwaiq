package com.saraha.paws.View.AccountViews.LoginAccount

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Repository.UserRepository

class LoginViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    var loginInResponseLiveData = MutableLiveData<Pair<Boolean, Exception?>>()

    //Function to handle firebase repository for signing in a user
    fun signInUserInFirebase(email: String, password: String){
        UserRepository().signinUser(email, password).observeForever {
            loginInResponseLiveData.postValue(it)
        }
    }
}