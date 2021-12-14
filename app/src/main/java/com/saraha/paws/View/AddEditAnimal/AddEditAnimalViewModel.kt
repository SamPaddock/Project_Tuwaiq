package com.saraha.paws.View.AddEditAnimal

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddEditAnimalViewModel: ViewModel() {

    //User variable to get and set data between fragments and back to activity
    val nameLiveData = MutableLiveData<String>()
    val typeLiveData = MutableLiveData<String>()
    val locationLiveData = MutableLiveData<String>()
    val ageLiveData = MutableLiveData<String>()
    val genderLiveData = MutableLiveData<String>()
    val colorLiveData = MutableLiveData<String>()
    val personalityLiveData = MutableLiveData<String>()
    val groomingLiveData = MutableLiveData<String>()
    val medicalLiveData = MutableLiveData<String>()
    val photoLiveData = MutableLiveData<Uri>()

    //Function to set data entered by user and set live data variables
    fun setAnimalName(name: String){ nameLiveData.postValue(name) }
    fun setAnimalType(type: String){ typeLiveData.postValue(type) }
    fun setAnimalLocation(location: String){ locationLiveData.postValue(location) }
    fun setAnimalAge(age: String){ ageLiveData.postValue(age) }
    fun setAnimalGender(gender: String){ genderLiveData.postValue(gender) }
    fun setAnimalColor(color: String){ colorLiveData.postValue(color) }
    fun setAnimalPersonality(personality: String){ personalityLiveData.postValue(personality) }
    fun setAnimalGrooming(grooming: String){ groomingLiveData.postValue(grooming) }
    fun setAnimalMedical(medical: String){ medicalLiveData.postValue(medical) }
    fun setAnimalPhoto(photo: Uri){ photoLiveData.postValue(photo) }

}