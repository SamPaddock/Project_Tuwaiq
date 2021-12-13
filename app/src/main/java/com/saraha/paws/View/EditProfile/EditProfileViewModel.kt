package com.saraha.paws.View.EditProfile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Repository.CharityRepository
import com.saraha.paws.Repository.UserRepository
import java.util.HashMap

class EditProfileViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    val postedPhotoLiveData = MutableLiveData<String>()
    val editUserLiveData = MutableLiveData<Boolean>()

    //Function to get charity group names
    fun setGroupData(): MutableLiveData<List<String>> {
        val listOfCharitiesLiveData = MutableLiveData<List<String>>()

        CharityRepository().getAllCharities().observeForever {
            val listOfCharities = mutableListOf<String>()
            it.forEach {
                listOfCharities.add(it.name)
            }
            listOfCharitiesLiveData.postValue(listOfCharities)
        }

        return listOfCharitiesLiveData
    }

    fun setPhotoInFireStorage(photo: String){
        UserRepository().setPhotoInStorage(Uri.parse(photo)).observeForever {
            if (it.isNotEmpty()) postedPhotoLiveData.postValue(it)
        }
    }

    fun editUserInFirebase(user: HashMap<String, String?>){
        UserRepository().updateUserAccount(user).observeForever {
            if (it) editUserLiveData.postValue(it)
        }
    }

}