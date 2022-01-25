package com.saraha.paws.View.AccountViews.EditProfile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.User
import com.saraha.paws.Repository.CharityRepository
import com.saraha.paws.Repository.UserRepository
import com.saraha.paws.Util.AppSharedPreference
import java.util.HashMap

class EditProfileViewModel: ViewModel() {
    //Shared preference helper class object
    val sharedPref = AppSharedPreference

    //Variable to get liveData response from Firebase
    val postedPhotoLiveData = MutableLiveData<String>()
    val editUserLiveData = MutableLiveData<Pair<Boolean, Exception?>>()

    //Function to get charity group names
    fun setGroupData(): MutableLiveData<List<String>> {
        val listOfCharitiesLiveData = MutableLiveData<List<String>>()

        CharityRepository().getAll().observeForever { result ->
            val listOfCharities = mutableListOf<String>()
            result.forEach { listOfCharities.add(it.name) }
            listOfCharitiesLiveData.postValue(listOfCharities)
        }

        return listOfCharitiesLiveData
    }

    //Function to handle firebase repository for uploading photo
    fun setPhotoInFireStorage(photo: String){
        UserRepository().setPhotoInStorage(Uri.parse(photo)).observeForever {
            if (it.isNotEmpty()) postedPhotoLiveData.postValue(it)
        }
    }

    //Function to handle firebase repository for updating user info
    fun editUserInFirebase(user: HashMap<String, String?>){
        UserRepository().updateUserAccount(user).observeForever {
            editUserLiveData.postValue(it)
        }
    }

    //Function to saved user info in shared preference
    fun setSharedPreference(context: Context, user: User){
        sharedPref.write("uName", user.name)
        sharedPref.write("eName", user.email)
        sharedPref.write("mName", user.mobile)
        sharedPref.write("tName", user.type)
        sharedPref.write("gName", user.group)
        sharedPref.write("pName", user.photoUrl!!)
    }

}