package com.saraha.paws.View.AccountViews.Profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.paws.Model.User
import com.saraha.paws.Repository.UserRepository
import com.saraha.paws.Util.AppSharedPreference

class ProfileViewModel: ViewModel() {

    val sharedPref = AppSharedPreference()

    fun getUserInfo() = User(Firebase.auth.currentUser?.uid, sharedPref.read("pName",""),
        sharedPref.read("eName","")!!, null, sharedPref.read("uName","")!!,
        sharedPref.read("mName","")!!, sharedPref.read("gName","")!!,
        sharedPref.read("tName","")!!
    )

}