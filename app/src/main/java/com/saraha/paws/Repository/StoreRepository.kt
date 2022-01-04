package com.saraha.paws.Repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.saraha.paws.Model.Product
import com.saraha.paws.Model.Vendor

class StoreRepository {

    var dbFirestore: FirebaseFirestore? = null

    fun createDBFirestore(){
        dbFirestore = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true).build()
        dbFirestore!!.firestoreSettings = settings
    }

    fun getAll(): LiveData<Pair<List<Vendor>?, Exception?>> {
        if (dbFirestore == null) createDBFirestore()

        val liveDataStore = MutableLiveData<Pair<List<Vendor>?, Exception?>>()

        dbFirestore?.collection("Store")?.get()?.addOnCompleteListener {snapshot ->
            if (snapshot.isSuccessful && snapshot.result != null) {
                val listOfStores = mutableListOf<Vendor>()
                for (store in snapshot.result!!) {
                    if (store.data.isNotEmpty()){
                        val name = store.get("name") as String
                        //val dbStore =
                        //listOfStores.add(dbStore)
                    }
                }
                liveDataStore.postValue(Pair(listOfStores, null))
            }
        }?.addOnFailureListener {
            liveDataStore.postValue(Pair(null, it))
        }

        return liveDataStore
    }
}