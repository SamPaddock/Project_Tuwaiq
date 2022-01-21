package com.saraha.paws.Repository

import android.content.ContentValues
import android.content.ContentValues.TAG
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
                Log.d(TAG,"ProductRepository: - getAll: - : ${snapshot.result}")
            if (snapshot.isSuccessful && snapshot.result != null) {
                val listOfProducts = mutableListOf<Product>()
                for (product in snapshot.result!!) {
                    if (product.data.isNotEmpty()){
                        val name = product.get("name") as String
                        val description = product.get("description") as String
                        val weight = (product.get("weight") ?: 0.0) as Double
                        val price = (product.get("price") ?: 0.0) as Double
                        val quantity = (product.get("quantity") ?: 0) as Int
                        val category = product.get("category") as String
                        val photo = product.get("photo") as String
                        val type = product.get("type") as String
                        val dbProduct = Product(product.id, documentID, name, description, weight,
                            price, quantity, category, photo, type)
                        listOfProducts.add(dbProduct)
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