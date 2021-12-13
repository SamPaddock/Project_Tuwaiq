package com.saraha.paws.Repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.saraha.paws.Model.User

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


    fun signinUser(email: String, password: String): LiveData<Boolean>{
        if (dbFBAuth == null) createDBAuth()

        val liveDataUser = MutableLiveData<Boolean>()

        dbFBAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    liveDataUser.postValue(true)
                }
            }?.addOnFailureListener {
                liveDataUser.postValue(false)
            }

        return liveDataUser
    }

    fun signupUser(email: String, password: String, user: User?): LiveData<Boolean> {
        if (dbFBAuth == null) createDBAuth()

        val liveDataUser = MutableLiveData<Boolean>()


        dbFBAuth?.createUserWithEmailAndPassword(email,password)
            ?.addOnSuccessListener {
                liveDataUser.postValue(true)
            }?.addOnFailureListener {
                liveDataUser.postValue(false)
            }

        return liveDataUser
    }

    fun createUserAccount(newUser: HashMap<String, String?>): LiveData<Boolean>  {
        if (dbFirestore == null) createDBFirestore()

        val liveDataUser = MutableLiveData<Boolean>()

        val currentUser = Firebase.auth.currentUser?.uid

        dbFirestore?.collection("Users")?.document(currentUser.toString())
            ?.set(newUser)?.addOnCompleteListener {
                if (it.isSuccessful){
                    liveDataUser.postValue(true)
                }
            }?.addOnFailureListener {
                liveDataUser.postValue(false)
            }

        return liveDataUser
    }

    fun getUserAccount(): LiveData<User>{
        if (dbFirestore == null) createDBFirestore()

        val liveDataUser = MutableLiveData<User>()

        dbFirestore?.collection("Users")?.document(Firebase.auth.uid!!)?.get()
            ?.addOnCompleteListener {
                if (it.isSuccessful && it.result?.data?.isNotEmpty() == true){
                    val userData = it.result!!.data
                    Log.d(TAG,"UserRepository: - getUserAccount: - : ${userData}")
                    val email = userData?.get("email") as String
                    val name = userData.get("name") as String
                    val mobile = userData.get("mobile") as String
                    val group = userData.get("group") as String

                    val user = User(Firebase.auth.uid!!,null,email,null,name,mobile,group)
                    liveDataUser.postValue(user)
                }
            }?.addOnFailureListener {

            }
        return liveDataUser
    }
}
