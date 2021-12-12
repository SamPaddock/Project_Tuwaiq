package com.saraha.paws.View.RegisterAccount

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Charity
import com.saraha.paws.Model.User
import com.saraha.paws.Repository.UserRepository

class RegisterViewModel: ViewModel() {

    //User variable to get and set data between fragments
    var isPasswordCorrect = MutableLiveData<Boolean>()
    var isConfirmedPasswordCorrect = MutableLiveData<Boolean>()
    var emailLiveData = MutableLiveData<String>()
    var passwordLiveData = MutableLiveData<String>()
    var nameLiveData = MutableLiveData<String>()
    var mobileLiveData = MutableLiveData<String>()
    var groupLiveData = MutableLiveData<String>()

    //Variable to get liveData response from Firebase
    var signInResponseLiveData = MutableLiveData<Boolean>()
    var createAccountResponseLiveData = MutableLiveData<Boolean>()

    //Function to set data entered by user from RegisterPage1Fragment
    fun setEmailFromPage1(email: String){ emailLiveData.postValue(email) }

    //Function to set data entered by user from RegisterPage1Fragment
    fun setPasswordFromPage1(password: String){ passwordLiveData.postValue(password) }

    //Function to set name entered by user from RegisterPage2Fragment
    fun setNameFromPage2(name: String){ nameLiveData.postValue(name) }

    //Function to set group entered by user from RegisterPage2Fragment
    fun setGroupFromPage2(group: String){ mobileLiveData.postValue(group) }

    //Function to set mobile entered by user from RegisterPage2Fragment
    fun setMobileFromPage2(mobile: String){ groupLiveData.postValue(mobile) }

    //Function to set password Validation
    fun setPasswordValidation(bool: Boolean){ isPasswordCorrect.postValue(bool) }

    //Function to set confirmed password Validation
    fun setConfirmedPasswordValidation(bool: Boolean){ isConfirmedPasswordCorrect.postValue(bool) }

    //Function to get charity group names
    fun setGroupData(): List<Charity>{
        var list = mutableListOf<Charity>()
        //TODO: get list from firebaseStorage
        return list
    }

    //Function to handle firebase repository for signing up a user
    fun signUpUserInFirebase(email: String, password: String){
        UserRepository().signupUser(email, password, null).observeForever {
            signInResponseLiveData.postValue(it)
        }
    }

    //Function to handle firebase repository for creating an account for a user
    fun createAnAccountInFirebase(newUser: HashMap<String, String?>){
        UserRepository().createUserAccount(newUser).observeForever {
            createAccountResponseLiveData.postValue(it)
        }
    }
}