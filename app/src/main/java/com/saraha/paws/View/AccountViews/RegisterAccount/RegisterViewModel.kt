package com.saraha.paws.View.AccountViews.RegisterAccount

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Repository.CharityRepository
import com.saraha.paws.Repository.UserRepository
import com.saraha.paws.Util.UserHelper

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
    var signInResponseLiveData = MutableLiveData<Pair<Boolean, Exception?>>()
    var createAccountResponseLiveData = MutableLiveData<Pair<Boolean, Exception?>>()

    //Function to set data entered by user from RegisterPage1Fragment
    fun setEmailFromPage1(email: String){ emailLiveData.postValue(email) }
    fun setPasswordFromPage1(password: String){ passwordLiveData.postValue(password) }
    fun setNameFromPage2(name: String){ nameLiveData.postValue(name) }
    fun setGroupFromPage2(group: String){ groupLiveData.postValue(group) }
    fun setMobileFromPage2(mobile: String){ mobileLiveData.postValue(mobile) }

    //Function to set password Validation
    fun setPasswordValidation(bool: Boolean){ isPasswordCorrect.postValue(bool) }
    fun setConfirmedPasswordValidation(bool: Boolean){ isConfirmedPasswordCorrect.postValue(bool) }

    //Function to get charity group names
    fun setGroupData(): MutableLiveData<List<String>>{
        val listOfCharitiesLiveData = MutableLiveData<List<String>>()

        CharityRepository().getAll().observeForever { list ->
            val listOfCharities = mutableListOf<String>()
            list.forEach { listOfCharities.add(it.name) }
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

    //Check email textField and handle use cases
    fun validateEmail(email: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().emailVerification(email.text.toString())
        handleTextFields(email,result.string,index,isValid)
    }

    //Check password textField and handle use cases
    fun validatePassword(password: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().passwordValidation(password.text.toString())
        handleTextFields(password,result.string,index,isValid)
    }

    //Check confirmed password textField and handle use cases
    fun validateConfirmPassword(
        confirmPassword: TextInputEditText, password: TextInputEditText, index: Int
    ) {
        val (result, isValid) = UserHelper().passwordValidation(
            confirmPassword.text.toString(), password.text.toString())
        handleTextFields(confirmPassword,result.string,index,isValid)
    }

    //Check mobile textField and handle use cases
    fun validateMobile(mobile: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().mobileValidation(mobile.text.toString())
        handleTextFields(mobile, result.string, index, isValid)
    }

    //Check name textField and handle use cases
    fun validateName(name: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().fieldVerification(name.text.toString())
        handleTextFields(name, result.string, index, isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
        } else {
            v.error = null
            when (index){
                0 -> setEmailFromPage1(v.text.toString())
                1 -> {
                    setPasswordValidation(isValid)
                    setPasswordFromPage1(v.text.toString())
                }
                2 -> setConfirmedPasswordValidation(isValid)
                3 -> setNameFromPage2(v.text.toString())
                4 -> setMobileFromPage2(v.text.toString())
                else -> return
            }
        }
    }
}