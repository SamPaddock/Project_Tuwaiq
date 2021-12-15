package com.saraha.paws.View.AccountViews.RegisterAccount

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Repository.CharityRepository
import com.saraha.paws.Repository.UserRepository

class RegisterViewModel: ViewModel() {

    //User variable to get and set data between fragments and back to activity
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
    fun setPasswordFromPage1(password: String){ passwordLiveData.postValue(password) }
    fun setNameFromPage2(name: String){ nameLiveData.postValue(name) }
    fun setGroupFromPage2(group: String){ mobileLiveData.postValue(group) }
    fun setMobileFromPage2(mobile: String){ groupLiveData.postValue(mobile) }

    //Function to set password Validation
    fun setPasswordValidation(bool: Boolean){ isPasswordCorrect.postValue(bool) }
    fun setConfirmedPasswordValidation(bool: Boolean){ isConfirmedPasswordCorrect.postValue(bool) }

    //Function to get charity group names
    fun setGroupData(): MutableLiveData<List<String>>{
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

    //Function to handle firebase repository for signing up a user
    fun signUpUserInFirebase(email: String, password: String){
        UserRepository().signupUser(email, password).observeForever {
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