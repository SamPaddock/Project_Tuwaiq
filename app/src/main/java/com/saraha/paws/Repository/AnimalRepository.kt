package com.saraha.paws.Repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.saraha.paws.Model.Animal

class AnimalRepository {

    var dbFirestore: FirebaseFirestore? = null

    fun createDBFirestore(){
        dbFirestore = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true).build()
        dbFirestore!!.firestoreSettings = settings
    }

    fun getAll(): LiveData<List<Animal>> {
        if (dbFirestore == null) createDBFirestore()

        val liveDataAnimal = MutableLiveData<List<Animal>>()

        dbFirestore?.collection("Animals")?.get()?.addOnCompleteListener {snapshot ->
            if (snapshot.isSuccessful && snapshot.result != null) {
                val listOfAnimals = mutableListOf<Animal>()
                for (animal in snapshot.result!!) {
                    if (animal.data.isNotEmpty()){
                        val name = animal.get("name") as String
                        val type = animal.get("type") as String
                        val age = animal.get("age") as String
                        val states = animal.get("states") as String
                        val gender = animal.get("gender") as String
                        val color = animal.get("color") as String
                        val personality = animal.get("personality") as String
                        val grooming = animal.get("grooming") as String
                        val medical = animal.get("medical") as String
                        val photoUrl = animal.get("photoUrl") as String
                        val volunteerID = animal.get("volunteerID") as String
                        val volunteerName = animal.get("volunteerName") as String
                        val groupName = animal.get("groupName") as String
                        val latitude = animal.get("latitude") as Double
                        val longitude = animal.get("longitude") as Double
                        val dbAnimal = Animal(animal.id, name, type, age, states, gender, color
                            , personality, grooming, medical, photoUrl, latitude, longitude,
                            volunteerID, volunteerName, groupName)
                        listOfAnimals.add(dbAnimal)
                    }
                }
                liveDataAnimal.postValue(listOfAnimals)
            }
        }?.addOnFailureListener {
            Log.d(ContentValues.TAG,"CharityRepository: - getAllCharities: - : ${it.message}")
        }

        return liveDataAnimal
    }
}