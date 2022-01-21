package com.saraha.paws.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.saraha.paws.Model.Vendor

class StoreRepository {

    var dbFirestore: FirebaseFirestore? = null

    fun createDBFirestore(){
        dbFirestore = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
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
                        val branch = store.get("branch") as String
                        val about = store.get("about") as String
                        val latitude = store.get("latitude") as Double
                        val longitude = store.get("longitude") as Double
                        val link = store.get("link") as String
                        val phone = store.get("phone") as String
                        val email = store.get("email") as String
                        val type = store.get("type") as String
                        val photo = store.get("photo") as String
                        val dbStore = Vendor(store.id, name, branch, about, latitude, longitude, link,
                            phone, email, type, photo)
                        listOfStores.add(dbStore)
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