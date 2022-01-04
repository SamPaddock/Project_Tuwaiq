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

    fun getAll(collection: String, documentID: String): LiveData<Pair<List<Product>?, Exception?>> {
        if (dbFirestore == null) createDBFirestore()

        val liveDataProduct = MutableLiveData<Pair<List<Product>?, Exception?>>()

        dbFirestore?.collection(collection)?.document(documentID)
            ?.collection("Product")?.get()?.addOnCompleteListener {snapshot ->
            if (snapshot.isSuccessful && snapshot.result != null) {
                val listOfProducts = mutableListOf<Product>()
                for (product in snapshot.result!!) {
                    if (product.data.isNotEmpty()){
                        val name = product.get("name") as String
                        //val dbProduct =
                        //listOfProducts.add(dbProduct)
                    }
                }
                liveDataProduct.postValue(Pair(listOfProducts, null))
            }
        }?.addOnFailureListener {
                liveDataProduct.postValue(Pair(null, it))
        }

        return liveDataProduct
    }
}