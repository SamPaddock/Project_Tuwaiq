package com.saraha.paws.Repository

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.collections.HashMap

class FirebaseRepository {

    var dbFirestore: FirebaseFirestore = Firebase.firestore
    var dbFBStorage: FirebaseStorage = Firebase.storage

    fun createDBFirestore(){
        dbFirestore = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true).build()
        dbFirestore.firestoreSettings = settings
    }
    fun createDBStorage(){dbFBStorage = Firebase.storage}

    fun setPhotoInStorage(fileUri: Uri): LiveData<String> {
        createDBStorage()

        val fileName = UUID.randomUUID().toString() +".jpg"

        val liveDataImage = MutableLiveData<String>()

        val ref = dbFBStorage.reference.child(Firebase.auth.uid.toString()).child(fileName)

        val uploadTask = ref.putFile(fileUri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                Log.d(ContentValues.TAG,"could not upload image: ${task.result?.error}")
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d(ContentValues.TAG, downloadUri.toString())
                liveDataImage.postValue(downloadUri.toString())
            }
        }.addOnFailureListener{
            Log.d(ContentValues.TAG,"could not upload image: ${it.message}")
        }
        return liveDataImage
    }

    fun addDocument(collection: String, newDocument: HashMap<String, String?>): LiveData<Boolean> {
        createDBFirestore()

        val liveDataCharity = MutableLiveData<Boolean>()

        dbFirestore.collection(collection).add(newDocument)
            .addOnCompleteListener {
                if (it.isSuccessful) liveDataCharity.postValue(true)
            }.addOnFailureListener {
                liveDataCharity.postValue(false)
            }

        return liveDataCharity
    }

    fun editDocument(collection: String, id: String, updateDocument: HashMap<String, String?>): LiveData<Boolean> {
        createDBFirestore()

        val liveDataCharity = MutableLiveData<Boolean>()

        dbFirestore.collection(collection).document(id).set(updateDocument)
            .addOnCompleteListener {
                if (it.isSuccessful) liveDataCharity.postValue(true)
            }.addOnFailureListener {
                liveDataCharity.postValue(false)
            }

        return liveDataCharity
    }

}