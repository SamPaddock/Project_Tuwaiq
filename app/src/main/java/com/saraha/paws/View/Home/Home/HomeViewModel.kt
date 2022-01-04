package com.saraha.paws.View.Home.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.User
import com.saraha.paws.Repository.UserRepository
import com.saraha.paws.Util.AppSharedPreference

class HomeViewModel: ViewModel() {
    //Shared preference helper class object
    val sharedPref = AppSharedPreference()

    //Variable to get liveData response from Firebase
    val livedataUser = MutableLiveData<User>()

    //Function to handle firebase repository response to retrieving user data
    fun getUserDataFromFirebase(){
        UserRepository().getUserAccount().observeForever {
            val user = it.first
            if (user != null && it.first?.email?.isNotEmpty() == true){
                //saved user info in shared preference
                sharedPref.write("uName", user.name)
                sharedPref.write("eName", user.email)
                sharedPref.write("mName", user.mobile)
                sharedPref.write("tName", user.type)
                sharedPref.write("gName", user.group)
                livedataUser.postValue(user!!)
            }
        }
    }

}