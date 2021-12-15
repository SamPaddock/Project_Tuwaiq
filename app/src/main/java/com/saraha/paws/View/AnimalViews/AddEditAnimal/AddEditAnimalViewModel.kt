package com.saraha.paws.View.AnimalViews.AddEditAnimal

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saraha.paws.Repository.FirebaseRepository
import kotlin.collections.HashMap

class AddEditAnimalViewModel: ViewModel() {

    //User variable to get and set data between fragments and back to activity
    val nameLiveData = MutableLiveData<String>()
    val typeLiveData = MutableLiveData<String>()
    val locationLiveData = MutableLiveData<String>()
    val ageLiveData = MutableLiveData<String>()
    val statusLiveData = MutableLiveData<String>()
    val genderLiveData = MutableLiveData<String>()
    val colorLiveData = MutableLiveData<String>()
    val personalityLiveData = MutableLiveData<String>()
    val groomingLiveData = MutableLiveData<String>()
    val medicalLiveData = MutableLiveData<String>()
    val photoLiveData = MutableLiveData<Uri>()

    //Variable to get liveData response from Firebase
    val postedPhotoLiveData = MutableLiveData<String>()
    val createdAnimalLiveData = MutableLiveData<Boolean>()
    val editAnimalLiveData = MutableLiveData<Boolean>()

    //Function to set data entered by user and set live data variables
    fun setAnimalName(name: String){ nameLiveData.postValue(name) }
    fun setAnimalType(type: String){ typeLiveData.postValue(type) }
    fun setAnimalLocation(location: String){ locationLiveData.postValue(location) }
    fun setAnimalAge(age: String){ ageLiveData.postValue(age) }
    fun setAnimalStatus(status: String){ statusLiveData.postValue(status) }
    fun setAnimalGender(gender: String){ genderLiveData.postValue(gender) }
    fun setAnimalColor(color: String){ colorLiveData.postValue(color) }
    fun setAnimalPersonality(personality: String){ personalityLiveData.postValue(personality) }
    fun setAnimalGrooming(grooming: String){ groomingLiveData.postValue(grooming) }
    fun setAnimalMedical(medical: String){ medicalLiveData.postValue(medical) }
    fun setAnimalPhoto(photo: Uri){ photoLiveData.postValue(photo) }

    fun setPhotoInFireStorage(photo: String){
        FirebaseRepository().setPhotoInStorage(Uri.parse(photo)).observeForever {
            if (it.isNotEmpty()) postedPhotoLiveData.postValue(it)
        }
    }

    fun createAAnimalInFirebase(animal: HashMap<String, String?>){
        FirebaseRepository().addDocument("Animals", animal).observeForever {
            createdAnimalLiveData.postValue(it)
        }
    }

    fun editAAnimalInFirebase(id: String, animal: HashMap<String, String?>){
        FirebaseRepository().editDocument("Animals", id, animal).observeForever {
            editAnimalLiveData.postValue(it)
        }
    }

    fun getStatusList(): List<String>{
        return listOf("Adopted", "Fostered", "For Adoption", "Vet: pre-check"
            , "Vet: post-check", "Vet: post-surgery", "Action: Rescue")
    }

    fun getTypeList(): List<String>{
        return listOf("Cat", "Dog", "Bird", "Aqua", "Rabbit", "Farm Animal", "Other")
    }

}