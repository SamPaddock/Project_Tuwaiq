package com.saraha.paws.View.CharityViews.AddEditCharity

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Repository.FirebaseRepository
import com.saraha.paws.Util.UserHelper
import kotlin.collections.HashMap

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
    val locationLiveData = MutableLiveData<LatLng>()

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
    fun setCharityLocation(location: LatLng) {locationLiveData.postValue(location)}

    //Function to handle firebase repository for uploading photo
    fun setPhotoInFireStorage(photo: String){
        FirebaseRepository().setPhotoInStorage(Uri.parse(photo)).observeForever {
            if (it.isNotEmpty()) postedPhotoLiveData.postValue(it)
        }
    }

    //Function to handle firebase repository for creating a charity document
    fun createACharityInFirebase(charity: HashMap<String, Any?>){
        FirebaseRepository().addDocument("Charities",charity).observeForever {
            createdCharityLiveData.postValue(it)
        }
    }

    //Function to handle firebase repository for updating a charity document
    fun editACharityInFirebase(id: String, charity: HashMap<String, Any?>){
        FirebaseRepository().editDocument("Charities",id, charity).observeForever {
            editCharityLiveData.postValue(it)
        }
    }

    //Check email textField and handle use cases
    fun validateEmail(email: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().emailVerification(email.text.toString())
        handleTextFields(email,result.string,0,isValid)
    }

    //Check mobile textField and handle use cases
    fun validateMobile(mobile: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().mobileValidation(mobile.text.toString())
        handleTextFields(mobile,result.string,index,isValid)
    }

    //Check name textField and handle use cases
    fun validateText(string: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().fieldVerification(string.text.toString())
        handleTextFields(string,result.string,index,isValid)
    }

    //Check facebook link from textField and handle use cases
    fun validateLink(string: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().fieldVerification(string.text.toString())
        handleTextFields(string,result.string,index,isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
        } else {
            v.error = null
            when (index){
                0 -> setCharityEmail(v.text.toString())
                1 -> setCharityMobile(v.text.toString())
                2 -> setCharityName(v.text.toString())
                3 -> setCharityFounder(v.text.toString())
                4 -> setCharitySTCPay(v.text.toString())
                5 -> setCharityFacebookLink(v.text.toString())
                6 -> setCharityInstagramLink(v.text.toString())
                else -> return
            }
        }
    }
}