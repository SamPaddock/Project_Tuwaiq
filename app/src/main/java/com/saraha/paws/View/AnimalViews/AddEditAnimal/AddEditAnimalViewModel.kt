package com.saraha.paws.View.AnimalViews.AddEditAnimal

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.Animal
import com.saraha.paws.R
import com.saraha.paws.Repository.FirebaseRepository
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.Util.toast
import kotlin.collections.HashMap

class AddEditAnimalViewModel: ViewModel() {

    //User variable to get and set data between fragments and back to activity
    val nameLiveData = MutableLiveData<String>()
    val typeLiveData = MutableLiveData<String>()
    val ageLiveData = MutableLiveData<String>()
    val statusLiveData = MutableLiveData<String>()
    val genderLiveData = MutableLiveData<String>()
    val colorLiveData = MutableLiveData<String>()
    val personalityLiveData = MutableLiveData<String>()
    val groomingLiveData = MutableLiveData<String>()
    val medicalLiveData = MutableLiveData<String>()
    val photoLiveData = MutableLiveData<Uri>()
    val locationLiveData = MutableLiveData<LatLng>()

    //Variable to get liveData response from Firebase
    val postedPhotoLiveData = MutableLiveData<String>()
    val createdAnimalLiveData = MutableLiveData<Boolean>()
    val editAnimalLiveData = MutableLiveData<Boolean>()

    var isTextValid = true

    //Function to set data entered by user and set live data variables
    fun setAnimalName(name: String){ nameLiveData.postValue(name) }
    fun setAnimalType(type: String){ typeLiveData.postValue(type) }
    fun setAnimalAge(age: String){ ageLiveData.postValue(age) }
    fun setAnimalStatus(status: String){ statusLiveData.postValue(status) }
    fun setAnimalGender(gender: String){ genderLiveData.postValue(gender) }
    fun setAnimalColor(color: String){ colorLiveData.postValue(color) }
    fun setAnimalPersonality(personality: String){ personalityLiveData.postValue(personality) }
    fun setAnimalGrooming(grooming: String){ groomingLiveData.postValue(grooming) }
    fun setAnimalMedical(medical: String){ medicalLiveData.postValue(medical) }
    fun setAnimalPhoto(photo: Uri){ photoLiveData.postValue(photo) }
    fun setAnimalLocation(location: LatLng) {locationLiveData.postValue(location)}

    //Function to upload values from view
    fun uploadValues(actionType: String, animal: Animal){
        if (!Patterns.WEB_URL.matcher(animal.photoUrl).matches()){
            setPhotoInFireStorage(animal.photoUrl)
            postedPhotoLiveData.observeForever{ checkActionToPerform(actionType, animal, it) }
        } else {
            checkActionToPerform(actionType, animal)
        }
    }

    //Function to check type of activity and call action
    private fun checkActionToPerform(actionType: String, animal: Animal, it: String = animal.photoUrl) {
        if (it.isNotEmpty()) {
            if (actionType == "Edit") {
                editAAnimalInFirebase(animal.aid!!, animal.getHashMap(it))
            } else {
                createAAnimalInFirebase(animal.getHashMap(it))
            }
        }
    }

    //Function to handle firebase repository for uploading photo
    fun setPhotoInFireStorage(photo: String){
        FirebaseRepository().setPhotoInStorage(Uri.parse(photo)).observeForever {
            if (it.isNotEmpty()) postedPhotoLiveData.postValue(it)
        }
    }

    //Function to handle firebase repository for creating an animal document
    fun createAAnimalInFirebase(animal: HashMap<String, Any?>){
        FirebaseRepository().addDocument("Animals", animal).observeForever {
            createdAnimalLiveData.postValue(it)
        }
    }

    //Function to handle firebase repository for updating an animal document
    fun editAAnimalInFirebase(id: String, animal: HashMap<String, Any?>){
        FirebaseRepository().editDocument("Animals", id, animal).observeForever {
            editAnimalLiveData.postValue(it)
        }
    }

    //Check text from textField and handle use cases
    fun validateText(edittext: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().fieldVerification(edittext.text.toString())
        handleTextFields(edittext,result.string,index,isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            isTextValid = false
            v.error = msg
        } else {
            isTextValid = true
            v.error = null
            when (index){
                0 -> setAnimalName(v.text.toString())
                1 -> setAnimalAge(v.text.toString())
                2 -> setAnimalColor(v.text.toString())
                3 -> setAnimalGender(v.text.toString())
                4 -> setAnimalPersonality(v.text.toString())
                5 -> setAnimalGrooming(v.text.toString())
                6 -> setAnimalMedical(v.text.toString())
                else -> return
            }
        }
    }

}