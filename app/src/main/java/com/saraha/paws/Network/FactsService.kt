package com.saraha.paws.Network

import com.saraha.paws.Model.Facts.CatFacts
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FactsService {

    @GET("/facts?limit=10")
    suspend fun getFact(): Response<CatFacts>

    @GET("/facts")
    suspend fun getNextFact(@Query("page")page: Int): Response<CatFacts>

    companion object{
        val base_url = "https://catfact.ninja"

        private val retrofit: Retrofit
        init {
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(base_url).build()
        }

        fun getInstance() = retrofit.create(FactsService::class.java)
    }
}