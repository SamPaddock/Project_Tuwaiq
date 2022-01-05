package com.saraha.paws.Repository

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
        val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
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
                        val about = vet.get("about") as String
                        val latitude = vet.get("latitude") as Double
                        val longitude = vet.get("longitude") as Double
                        val link = vet.get("link") as String
                        val phone = vet.get("phone") as String
                        val email = vet.get("email") as String
                        val type = vet.get("type") as String
                        val photo = vet.get("photo") as String
                        val dbVet = Vendor(vet.id, name, about, latitude, longitude, link,
                            phone, email, type, photo)
                        listOfVets.add(dbVet)
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