package com.saraha.paws.View.ShowFacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Database.AppDatabase
import com.saraha.paws.Database.DatabaseClient
import com.saraha.paws.Model.Facts.CatFacts
import com.saraha.paws.Repository.FactsRepository
import com.saraha.paws.Util.AppSharedPreference
import com.saraha.paws.Util.SharedConst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

enum class DataStatus(var string: String){
    New("new"),
    Room("room"),
    Next("next"),
    Error("could not retrieve"),
    Success("Saved"),
    Fail("failed")
}

class DisplayFactsViewModel: ViewModel() {

    //Database and shared preference variables
    var dbClient: DatabaseClient? = null
    var db: AppDatabase? = null
    //val sharedPref = AppSharedPreference()

    val factsLiveData = MutableLiveData<Pair<CatFacts?,DataStatus>>()

    fun checkIfRoomIsEmpty(){
        val roomData = db?.catFactsDao()?.get()
        //val factTime = sharedPref.read(SharedConst.PrefsFactDate.string, (-1).toLong())

        if (roomData?.isNotEmpty() == true){ //&& factTime != (-1).toLong()){

            val currentDateTime = Calendar.getInstance().timeInMillis

            if (currentDateTime <= currentDateTime){
                val nextPage = if (roomData[0].last_page != roomData[0].current_page+1) roomData[0].current_page+1 else 1
                getNextPageOfFacts(nextPage)
            } else {
                factsLiveData.postValue(Pair(db?.catFactsDao()?.get()?.get(0),DataStatus.Room))
            }

        } else {
            getFacts()
        }
    }

    fun getFacts(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = FactsRepository().getAllFacts()
            withContext(Dispatchers.Main){
                if (!response.isSuccessful) {
                    factsLiveData.postValue(Pair(null, DataStatus.Error))
                    return@withContext
                }

                response.body()?.let { list ->
                    val facts = if (list.data?.isNotEmpty() == true) list else null
                    factsLiveData.postValue(Pair(facts.takeIf { facts != null },DataStatus.New))
                }
            }
        }
    }

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

    fun saveFactIntoRoom(catFacts: CatFacts?){
        if (catFacts != null) {
            db?.catFactsDao()?.delete()
            db?.catFactsDao()?.insert(catFacts)
        }
    }


}