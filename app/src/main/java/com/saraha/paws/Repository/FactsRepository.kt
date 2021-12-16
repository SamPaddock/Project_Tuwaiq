package com.saraha.paws.Repository

import com.saraha.paws.Network.FactsService

class FactsRepository {

    private val retrofitService = FactsService.getInstance()

    suspend fun getAllFacts() = retrofitService.getFact()

    suspend fun getNextFacts(page: Int) = retrofitService.getNextFact(page)

}