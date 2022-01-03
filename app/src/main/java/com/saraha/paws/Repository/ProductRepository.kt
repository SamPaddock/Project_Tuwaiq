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

class ProductRepository {

    var dbFirestore: FirebaseFirestore? = null

    fun createDBFirestore(){
        dbFirestore = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true).build()
        dbFirestore!!.firestoreSettings = settings
    }

    fun getAll(): LiveData<List<Product>> {
        if (dbFirestore == null) createDBFirestore()

        val liveDataProduct = MutableLiveData<List<Product>>()

        dbFirestore?.collection("Animals")?.get()?.addOnCompleteListener {snapshot ->
            if (snapshot.isSuccessful && snapshot.result != null) {
                val listOfProducts = mutableListOf<Product>()
                for (animal in snapshot.result!!) {
                    if (animal.data.isNotEmpty()){
                        val name = animal.get("name") as String
                        //val dbProduct =
                        //listOfProducts.add(dbAnimal)
                    }
                }
                liveDataProduct.postValue(listOfProducts)
            }
        }?.addOnFailureListener {
            Log.d(ContentValues.TAG,"CharityRepository: - getAllCharities: - : ${it.message}")
        }

        return liveDataProduct
    }
}