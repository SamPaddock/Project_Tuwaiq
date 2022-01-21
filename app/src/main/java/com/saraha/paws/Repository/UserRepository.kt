package com.saraha.paws.Repository

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.saraha.paws.Model.User
import java.util.*
import kotlin.collections.HashMap

class UserRepository {

    var dbFirestore: FirebaseFirestore? = null
    var dbFBStorage: FirebaseStorage? = null
    var dbFBAuth: FirebaseAuth? = null

    fun createDBFirestore(){
        dbFirestore = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true).build()
        dbFirestore!!.firestoreSettings = settings
    }
    fun createDBStorage(){dbFBStorage = Firebase.storage}
    fun createDBAuth(){dbFBAuth = Firebase.auth}


    fun signinUser(email: String, password: String): LiveData<Pair<Boolean, Exception?>>{
        if (dbFBAuth == null) createDBAuth()

        val liveDataUser = MutableLiveData<Pair<Boolean, Exception?>>()

        dbFBAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { result ->
                if (result.isSuccessful) { liveDataUser.postValue(Pair(true, null)) }
            }?.addOnFailureListener {
                liveDataUser.postValue(Pair(false, it))
            }

        return liveDataUser
    }

    fun signupUser(email: String, password: String): LiveData<Pair<Boolean, Exception?>> {
        if (dbFBAuth == null) createDBAuth()

        val liveDataUser = MutableLiveData<Pair<Boolean, Exception?>>()


        dbFBAuth?.createUserWithEmailAndPassword(email,password)
            ?.addOnSuccessListener {
                liveDataUser.postValue(Pair(true, null))
            }
            ?.addOnFailureListener { liveDataUser.postValue(Pair(false, it)) }

        return liveDataUser
    }

    fun createUserAccount(newUser: HashMap<String, String?>): LiveData<Pair<Boolean, Exception?>>{
        if (dbFirestore == null) createDBFirestore()

        val liveDataUser = MutableLiveData<Pair<Boolean, Exception?>>()

        val currentUser = Firebase.auth.currentUser?.uid

        dbFirestore?.collection("Users")?.document(currentUser.toString())
            ?.set(newUser)?.addOnCompleteListener {
                if (it.isSuccessful){ liveDataUser.postValue(Pair(true, null)) }
            }?.addOnFailureListener { liveDataUser.postValue(Pair(false, it)) }

        return liveDataUser
    }

    fun getUserAccount(): LiveData<Pair<User?, Exception?>>{
        if (dbFirestore == null) createDBFirestore()

        val liveDataUser = MutableLiveData<Pair<User?, Exception?>>()

        dbFirestore?.collection("Users")?.document(Firebase.auth.uid!!)?.get()
            ?.addOnCompleteListener {
                if (it.isSuccessful && it.result?.data?.isNotEmpty() == true){
                    val userData = it.result!!.data
                    Log.d(TAG,"UserRepository: - getUserAccount: - : ${userData}")
                    val email = userData?.get("email") as String
                    val name = userData.get("name") as String
                    val photoUrl = userData.get("photoUrl") ?: " "
                    val mobile = userData.get("mobile") as String
                    val group = userData.get("group") as String
                    val type = userData.get("type") as String

                    val user = User(Firebase.auth.uid!!, photoUrl.toString() ,email, null,
                        name, mobile, group, type)
                    liveDataUser.postValue(Pair(user, null))
                }
            }?.addOnFailureListener { liveDataUser.postValue(Pair(null, it)) }

        return liveDataUser
    }

    fun setPhotoInStorage(fileUri: Uri): LiveData<String>{
        if (dbFBStorage == null) createDBStorage()

        val fileName = UUID.randomUUID().toString() +".jpg"

        val liveDataImage = MutableLiveData<String>()

        var ref = dbFBStorage?.reference?.child(Firebase.auth.uid.toString())?.child(fileName)

        val uploadTask = ref?.putFile(fileUri)
        val tasks = uploadTask?.continueWithTask { task ->
            if (!task.isSuccessful) {
                Log.d(TAG,"could not upload image: ${task.result?.error}")
            }
            ref?.downloadUrl!!
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d(TAG, downloadUri.toString())
                liveDataImage.postValue(downloadUri.toString())
            }
        }?.addOnFailureListener{
            Log.d(TAG,"could not upload image: ${it.message}")
        }
        return liveDataImage
    }

    fun updateUserAccount(updateUser: HashMap<String, String?>): LiveData<Pair<Boolean, Exception?>>{
        if (dbFirestore == null) createDBFirestore()

        val liveDataUser = MutableLiveData<Pair<Boolean, Exception?>>()

        dbFirestore?.collection("Users")
            ?.document(Firebase.auth.currentUser?.uid!!)?.set(updateUser)
            ?.addOnCompleteListener {
                if (it.isSuccessful) liveDataUser.postValue(Pair(true, null))
            }?.addOnFailureListener { liveDataUser.postValue(Pair(false, it)) }

        return liveDataUser
    }
}
