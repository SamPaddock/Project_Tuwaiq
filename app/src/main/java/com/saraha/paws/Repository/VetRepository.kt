package com.saraha.paws.Repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.saraha.paws.Model.Vendor

class VetRepository {

    var dbFirestore: FirebaseFirestore? = null

    fun createDBFirestore(){
        dbFirestore = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true).build()
        dbFirestore!!.firestoreSettings = settings
    }

    fun getAll(): LiveData<Pair<List<Vendor>?, Exception?>> {
        if (dbFirestore == null) createDBFirestore()

        val liveDataVet = MutableLiveData<Pair<List<Vendor>?, Exception?>>()

        dbFirestore?.collection("Vets")?.get()?.addOnCompleteListener {snapshot ->
            if (snapshot.isSuccessful && snapshot.result != null) {
                val listOfVets = mutableListOf<Vendor>()
                for (vet in snapshot.result!!) {
                    if (vet.data.isNotEmpty()){
                        val name = vet.get("name") as String
                        //val dbVet =
                        //listOfVets.add(dbVet)
                    }
                }
                liveDataVet.postValue(Pair(listOfVets, null))
            }
        }?.addOnFailureListener {
            liveDataVet.postValue(Pair(null, it))
        }

        return liveDataVet
    }
}