package com.saraha.paws.Repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.saraha.paws.Model.Charity

class CharityRepository {

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

    fun getAllCharities(): LiveData<List<Charity>>{
        if (dbFirestore == null) createDBFirestore()

        val liveDataCharity = MutableLiveData<List<Charity>>()

        dbFirestore?.collection("Charities")?.get()?.addOnCompleteListener {snapshot ->
            if (snapshot.isSuccessful && snapshot.result != null) {
                val listOfCharities = mutableListOf<Charity>()
                for (charity in snapshot.result!!) {
                    if (!charity.data.isEmpty()){
                        val email = charity.get("email") as String
                        val name = charity.get("name") as String
                        val founder = charity.get("founder") as String
                        val mobile = charity.get("mobile") as String
                        val photo = charity.get("photo") as String
                        val facebookUrl = charity.get("facebookUrl") as String
                        val instagramUrl = charity.get("instagramUrl") as String
                        val dbCharity = Charity(charity.id, name, founder, email, mobile,
                            photo, facebookUrl, instagramUrl)
                        listOfCharities.add(dbCharity)
                    }
                }
                liveDataCharity.postValue(listOfCharities)
            }
        }?.addOnFailureListener {
            Log.d(TAG,"CharityRepository: - getAllCharities: - : ${it.message}")
        }

        return liveDataCharity
    }
}