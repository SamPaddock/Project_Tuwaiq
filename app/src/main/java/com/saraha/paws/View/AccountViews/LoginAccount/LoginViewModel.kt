package com.saraha.paws.View.AccountViews.LoginAccount

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.paws.Repository.UserRepository
import com.saraha.paws.Util.toast
import com.saraha.paws.View.Home.HomeActivity

class LoginViewModel: ViewModel() {

    //Variable to get liveData response from Firebase
    var loginInResponseLiveData = MutableLiveData<Pair<Boolean, Exception?>>()

    //Function to handle firebase repository for signing in a user
    fun signInUserInFirebase(email: String, password: String){
        UserRepository().signinUser(email, password).observeForever {
            loginInResponseLiveData.postValue(it)
        }
    }
}