package com.saraha.paws.View.Home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.User
import com.saraha.paws.Repository.UserRepository
import com.saraha.paws.Util.AppSharedPreference

class HomeViewModel: ViewModel() {

    val sharedPref = AppSharedPreference()

    //Variable to get liveData response from Firebase
    val livedataUser = MutableLiveData<User>()

    //Function to handle firebase repository response to retrieving user data
    fun getUserDataFromFirebase(){
        UserRepository().getUserAccount().observeForever {
            val user = it.first
            if (user != null && it.first?.email?.isNotEmpty() == true){
                Log.d(TAG,"HomeViewModel: - getUserDataFromFirebase: - : ${sharedPref}")
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