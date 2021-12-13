package com.saraha.paws.View.AddEditCharity

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Charity
import com.saraha.paws.Repository.CharityRepository
import java.util.HashMap

class AddEditCharityViewModel: ViewModel() {

    //User variable to get and set data between fragments and back to activity
    val emailLiveData = MutableLiveData<String>()
    val nameLiveData = MutableLiveData<String>()
    val founderLiveData = MutableLiveData<String>()
    val mobileLiveData = MutableLiveData<String>()
    val stcPayLiveData = MutableLiveData<String>()
    val facebookLinkLiveData = MutableLiveData<String>()
    val instagramLinkLiveData = MutableLiveData<String>()
    val photoLiveData = MutableLiveData<Uri>()

    //Variable to get liveData response from Firebase
    val postedPhotoLiveData = MutableLiveData<String>()
    val createdCharityLiveData = MutableLiveData<Boolean>()
    val editCharityLiveData = MutableLiveData<Boolean>()

    //Function to set data entered by user and set live data variables
    fun setCharityEmail(email: String){ emailLiveData.postValue(email) }
    fun setCharityName(name: String){ nameLiveData.postValue(name) }
    fun setCharityFounder(founder: String){ founderLiveData.postValue(founder) }
    fun setCharityMobile(mobile: String){ mobileLiveData.postValue(mobile) }
    fun setCharitySTCPay(mobile: String){ stcPayLiveData.postValue(mobile) }
    fun setCharityFacebookLink(link: String){ facebookLinkLiveData.postValue(link) }
    fun setCharityInstagramLink(link: String){ instagramLinkLiveData.postValue(link) }
    fun setCharityPhoto(imgData: Uri) {photoLiveData.postValue(imgData)}

    fun setPhotoInFireStorage(photo: String){
        CharityRepository().setPhotoInStorage(Uri.parse(photo)).observeForever {
            if (it.isNotEmpty()) postedPhotoLiveData.postValue(it)
        }
    }

    fun createACharityInFirebase(charity: HashMap<String, String?>){
        CharityRepository().addCharity(charity).observeForever {
            createdCharityLiveData.postValue(it)
        }
    }

    fun editACharityInFirebase(id: String, charity: HashMap<String, String?>){
        CharityRepository().editCharity(id, charity).observeForever {
            editCharityLiveData.postValue(it)
        }
    }
}