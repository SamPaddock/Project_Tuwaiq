package com.saraha.paws.View.AddEditAnimal

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.kofigyan.stateprogressbar.StateProgressBar
import com.saraha.paws.Model.Animal
import com.saraha.paws.R
import com.saraha.paws.View.AddEditAnimal.Fragment.AddEditAnimalPage1Fragment
import com.saraha.paws.View.AddEditAnimal.Fragment.AddEditAnimalPage2Fragment
import com.saraha.paws.View.AddEditAnimal.Fragment.AddEditAnimalPage3Fragment
import com.saraha.paws.databinding.ActivityAddEditAnimalBinding
import com.saraha.paws.databinding.FragmentAddEditAnimalPage3Binding

class AddEditAnimalActivity : AppCompatActivity() {

    private val viewModel: AddEditAnimalViewModel by viewModels()
    lateinit var binding: ActivityAddEditAnimalBinding

    private var actionType = ""
    var animal = Animal(null, "", "", "", "", "", "",
    "", "", "", "", "")
    var pageFragments = listOf(
        AddEditAnimalPage1Fragment(),
        AddEditAnimalPage2Fragment(),
        AddEditAnimalPage3Fragment()
    )
    var pageProgress = listOf(
        StateProgressBar.StateNumber.ONE,
        StateProgressBar.StateNumber.TWO,
        StateProgressBar.StateNumber.THREE
    )
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditAnimalBinding.inflate(layoutInflater)

        actionType = intent.getStringExtra("type").toString()

        if (actionType == "Edit"){
            binding.buttonCreateCharity.setText(getString(R.string.save))
            animal = intent.getSerializableExtra("animal") as Animal
        }

        //Set fragment view when activity is created
        displayFragment(pageFragments[index])

        //Get data coming from viewModel that is sent from fragment
        getCharityLiveData()

        setButtonOnClickListener()

        setContentView(binding.root)
    }

    private fun setButtonOnClickListener() {
        //set onClick listener for next button

        binding.buttonToAddEditAnimalNext.setOnClickListener {
            navigateBetweenFragments("Next")
        }

        //set onClick listener for previous button
        binding.buttonAddEditAnimalPrevious.setOnClickListener {
            navigateBetweenFragments("Pre")
        }

        binding.buttonCreateCharity.setOnClickListener {
            verifyAnimalFormFields()
        }
    }

    private fun verifyAnimalFormFields() {
        if (animal.isAllDataNotEmpty()) {
            viewModel.setPhotoInFireStorage(animal.photoUrl)
            viewModel.postedPhotoLiveData.observe(this) {
                if (it.isNotEmpty()) {
                    if (actionType == "Edit"){
                        editAnimal(it)
                    } else {
                        addAnimal(it)
                    }
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.all_required), Toast.LENGTH_SHORT).show()
        }
    }

    private fun editAnimal(photo: String){
        viewModel.editAAnimalInFirebase(
            animal.aid!!,
            animal.getHashMap(photo)
        )
        viewModel.editAnimalLiveData.observe(this){
            if (it) {
                Toast.makeText(
                    this,
                    getString(R.string.successful_edit_animal), Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.failure_edit_animal), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun addAnimal(photo: String) {
        viewModel.createAAnimalInFirebase(animal.getHashMap(photo))

        viewModel.createdAnimalLiveData.observe(this) {
            if (it) {
                Toast.makeText(
                    this,
                    getString(R.string.successful_add_animal), Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.failure_add_animal), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //Function to navigate between fragments
    private fun navigateBetweenFragments(type: String) {
        //set button visibility variable
        var (isNextVisible, isPreVisible, isBtnVisible) = Triple(false, false, false)
        //increment or decrement index when navigating between fragments
        if (type == "Next" && index != pageFragments.size - 1) index++
        else if (type == "Pre" && index != 0) index --
        //button visibility when navigating between fragments
        if (index == pageFragments.size - 1) {
            isNextVisible = false; isPreVisible = true; isBtnVisible = true
        } else {
            if (index == 0) {
                isNextVisible = true; isPreVisible = false; isBtnVisible = false
            } else {
                isNextVisible = true; isPreVisible = true; isBtnVisible = false
            }
        }
        //set fragment in activity
        setFragmentView(
            isNextVisible, isPreVisible, isBtnVisible,
            pageProgress[index],
            pageFragments[index]
        )
    }

    private fun setFragmentView(
        isNextVisible: Boolean, isPreVisible: Boolean, isBtnVisible: Boolean,
        progress: StateProgressBar.StateNumber,
        fragment: Fragment
    ) {
        showNavButton(isNextVisible, isPreVisible, isBtnVisible)
        binding.stateAddEditAnimalProgressBar.setCurrentStateNumber(progress)
        displayFragment(fragment)
    }

    private fun getCharityLiveData() {
        viewModel.nameLiveData.observe(this ,{ animal.name = it })
        viewModel.typeLiveData.observe(this ,{ animal.type = it })
        viewModel.locationLiveData.observe(this ,{ animal.location = it })
        viewModel.ageLiveData.observe(this ,{ animal.age = it})
        viewModel.statusLiveData.observe(this ,{ animal.states = it })
        viewModel.genderLiveData.observe(this ,{ animal.gender = it })
        viewModel.colorLiveData.observe(this ,{ animal.color = it })
        viewModel.personalityLiveData.observe(this ,{ animal.personality = it })
        viewModel.groomingLiveData.observe(this ,{ animal.grooming = it })
        viewModel.medicalLiveData.observe(this ,{ animal.medical = it })
        viewModel.photoLiveData.observe(this ,{ animal.photoUrl = it.toString() })
    }

    //Function to add or replace a fragment view
    private fun displayFragment(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putSerializable("animal", animal)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            binding.FrameLayoutAddEditAnimal.id,
            fragment,
        ).commit()
    }

    //Function to show or hide buttons in the activity depending on the fragment
    private fun showNavButton(isNextVisible: Boolean, isPreVisible: Boolean, isBtnVisible: Boolean){
        binding.buttonCreateCharity.visibility = isVisible(isBtnVisible)
        binding.buttonAddEditAnimalPrevious.visibility = isVisible(isPreVisible)
        binding.buttonToAddEditAnimalNext.visibility = isVisible(isNextVisible)
    }

    //Function to get the component visibility value
    private fun isVisible(visibility:Boolean) = if (visibility) View.VISIBLE else View.GONE
}