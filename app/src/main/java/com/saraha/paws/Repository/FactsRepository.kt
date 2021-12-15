package com.saraha.paws.Repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saraha.paws.Model.Facts.catFacts
import com.saraha.paws.Network.FactsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FactsRepository {

    private val retrofitService = FactsService.getInstance()

    suspend fun getAllFacts() = retrofitService.getFact()

    suspend fun getNextFacts(page: Int) = retrofitService.getNextFact(page)

}