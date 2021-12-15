package com.saraha.paws.View.ShowFacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Model.Facts.catFacts
import com.saraha.paws.Repository.FactsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DisplayFactsViewModel: ViewModel() {

    val factsLiveData = MutableLiveData<catFacts?>()

    fun getFacts(){

        CoroutineScope(Dispatchers.IO).launch {
            val response = FactsRepository().getAllFacts()
            withContext(Dispatchers.Main){
                if (!response.isSuccessful) {
                    factsLiveData.value = null
                    return@withContext
                }

                response.body()?.let { list ->
                    val facts = if (list.data.isNotEmpty()) list else null
                    factsLiveData.value = facts.takeIf { facts != null }
                }
            }
        }

    }

    fun getNextPageOfFacts(page: Int){

        CoroutineScope(Dispatchers.IO).launch {
            val response = FactsRepository().getNextFacts(page)
            withContext(Dispatchers.Main){
                if (!response.isSuccessful) {
                    factsLiveData.value = null
                    return@withContext
                }

                response.body()?.let { list ->
                    val facts = if (list.data.isNotEmpty()) list else null
                    factsLiveData.value = facts.takeIf { facts != null }
                }
            }
        }

    }


}