package com.saraha.paws.View.ShowFacts

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Database.AppDatabase
import com.saraha.paws.Database.DatabaseClient
import com.saraha.paws.Model.Facts.CatFacts
import com.saraha.paws.Repository.FactsRepository
import com.saraha.paws.Util.AppSharedPreference
import com.saraha.paws.Util.NetworkStatus
import com.saraha.paws.Util.SharedConst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

//Enum class to handle different cases
enum class DataStatus(var string: String){
    New("new"),
    Room("room"),
    Next("next"),
    Error("could not retrieve")
}

class DisplayFactsViewModel: ViewModel() {

    //Database and shared preference variables
    var dbClient: DatabaseClient? = null
    var db: AppDatabase? = null
    val sharedPref = AppSharedPreference

    //Variable to get liveData response from Api
    val factsLiveData = MutableLiveData<Pair<CatFacts?,DataStatus>>()

    //Function to check if database Room is empty, then check data save time, if save time is more than 24 hours old, then get new data
    fun checkIfRoomIsEmpty(context: Context) {
        val roomData = db?.catFactsDao()?.get()
        val factTime = sharedPref.read(SharedConst.PrefsFactDate.string, (-1).toLong())

        //check if Room is empty and if Api data has been retrieved before else get new data from Api
        if (roomData?.isNotEmpty() == true && factTime != (-1).toLong()){

            val currentDateTime = Calendar.getInstance()
            val setDateTime = Calendar.getInstance()
            setDateTime.timeInMillis = factTime!!

            //check if it has been 24 hours since data has been retrieve and if there is network connection else get from Room
            if (factTime <= currentDateTime.timeInMillis && NetworkStatus().isOnline(context)){
                val nextPage = if (roomData[0].last_page != roomData[0].current_page+1) roomData[0].current_page+1 else 1
                getNextPageOfFacts(nextPage)
            } else {
                factsLiveData.postValue(Pair(db?.catFactsDao()?.get()?.get(0),DataStatus.Room))
            }

        } else {
            getFacts()
        }
    }

    //Function to get data from Api
    fun getFacts(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = FactsRepository().getAllFacts()
            withContext(Dispatchers.Main){
                if (!response.isSuccessful) {
                    factsLiveData.postValue(Pair(null, DataStatus.Error))
                    return@withContext
                }

                response.body()?.let { list ->
                    val facts = if (list.data.isNotEmpty()) list else null
                    factsLiveData.postValue(Pair(facts.takeIf { facts != null },DataStatus.New))
                }
            }
        }
    }

    //Function to get data from the next page of Api
    fun getNextPageOfFacts(page: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val response = FactsRepository().getNextFacts(page)
            withContext(Dispatchers.Main){
                if (!response.isSuccessful) {
                    factsLiveData.postValue(Pair(null, DataStatus.Error))
                    return@withContext
                }

                response.body()?.let { list ->
                    val facts = if (list.data?.isNotEmpty() == true) list else null
                    factsLiveData.postValue(Pair(facts.takeIf { facts != null },DataStatus.Next))
                }
            }
        }
    }

    //Function to clean and save new data into database Room
    fun saveFactIntoRoom(catFacts: CatFacts?){
        if (catFacts != null) {
            db?.catFactsDao()?.delete()
            db?.catFactsDao()?.insert(catFacts)
        }
    }


}