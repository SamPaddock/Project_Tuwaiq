package com.saraha.paws.Repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRepository {

    var dbFirestore: FirebaseFirestore? = null
    //var dbFBStorage: FirebaseStorage? = null
    var dbFBAuth: FirebaseAuth? = null

    fun createDBFirestore(){
        dbFirestore = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true).build()
        dbFirestore!!.firestoreSettings = settings
    }
    //fun createDBStorage(){dbFBStorage = Firebase.storage}
    fun createDBAuth(){dbFBAuth = Firebase.auth}


    fun signupUser(email: String, password: String): LiveData<Boolean> {
        if (dbFBAuth == null) createDBAuth()

        val liveDataUser = MutableLiveData<Boolean>()

        Firebase.auth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                liveDataUser.postValue(true)
            }.addOnFailureListener {
                liveDataUser.postValue(false)
            }

        return liveDataUser
    }

    fun createUserAccount(newUser: HashMap<String, String?>): LiveData<Boolean>  {
        if (dbFirestore == null) createDBFirestore()

        val liveDataUser = MutableLiveData<Boolean>()

        val currentUser = Firebase.auth.currentUser?.uid

        Firebase.firestore.collection("Users").document(currentUser.toString())
            .set(newUser).addOnCompleteListener {
                if (it.isSuccessful){
                    liveDataUser.postValue(true)
                }
            }.addOnFailureListener {
                liveDataUser.postValue(false)
            }

        return liveDataUser
    }


}