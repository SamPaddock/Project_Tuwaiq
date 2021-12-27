package com.saraha.paws.View.AnimalViews.AddEditAnimal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.kofigyan.stateprogressbar.StateProgressBar
import com.saraha.paws.Model.Animal
import com.saraha.paws.R
import com.saraha.paws.Util.AppSharedPreference
import com.saraha.paws.Util.toast
import com.saraha.paws.View.AnimalViews.AddEditAnimal.Fragment.AddEditAnimalPage1Fragment
import com.saraha.paws.View.AnimalViews.AddEditAnimal.Fragment.AddEditAnimalPage2Fragment
import com.saraha.paws.View.AnimalViews.AddEditAnimal.Fragment.AddEditAnimalPage3Fragment
import com.saraha.paws.databinding.ActivityAddEditAnimalBinding

class AddEditAnimalActivity : AppCompatActivity() {
    //View model and binding lateinit property
    private val viewModel: AddEditAnimalViewModel by viewModels()
    lateinit var binding: ActivityAddEditAnimalBinding
    //Shared preference helper class object
    val sharedPref = AppSharedPreference()
    //Variables that will hold intent values
    private var actionType = ""
    var animal = Animal()
    //Fragment navigation arguments
    var pageFragments = listOf(AddEditAnimalPage1Fragment(), AddEditAnimalPage2Fragment(), AddEditAnimalPage3Fragment())
    var pageProgress = listOf(StateProgressBar.StateNumber.ONE, StateProgressBar.StateNumber.TWO, StateProgressBar.StateNumber.THREE)
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditAnimalBinding.inflate(layoutInflater)

        actionType = intent.getStringExtra("type").toString()

        if (actionType == "Edit"){
            binding.buttonAnimalCharity.setText(getString(R.string.save))
            animal = intent.getSerializableExtra("animal") as Animal
        }

        setupToolbar()

        //Set fragment view when activity is created
        displayFragment(pageFragments[index])

        //Get data coming from viewModel that is sent from fragment
        getAnimalLiveData()

        setButtonOnClickListener()

        setContentView(binding.root)
    }

    private fun setupToolbar() {
        val mainToolbar = binding.toolbarAddEditAnimal
        mainToolbar.title = ""
        mainToolbar.setNavigationIcon(R.drawable.ic_back_24)
        setSupportActionBar(mainToolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    //Function to set button onClick listener
    private fun setButtonOnClickListener() {
        //set onClick listener for next button
        binding.buttonToAddEditAnimalNext.setOnClickListener {
            checkTextValidationBeforeNavigation("Next")
        }

        //set onClick listener for previous button
        binding.buttonAddEditAnimalPrevious.setOnClickListener {
            checkTextValidationBeforeNavigation("Pre")
        }

        binding.buttonAnimalCharity.setOnClickListener { verifyAnimalFormFields() }
    }

    private fun checkTextValidationBeforeNavigation(navigationType: String) {
        if (viewModel.isTextValid) navigateBetweenFragments(navigationType)
        else this.toast(getString(R.string.all_required))
    }

    //Function to check all data is entered then send photo to FireStorage
    private fun verifyAnimalFormFields() {
        if (animal.isAllDataNotEmpty() && viewModel.isTextValid) {
            isUploadingContent(true)
            viewModel.uploadValues(actionType, animal)
            if (actionType == "Edit") { editAnimal() } else { addAnimal() }
        } else {
            isUploadingContent(false)
            this.toast(getString(R.string.all_required))
        }
    }

    private fun isUploadingContent(isUploading: Boolean) {
        binding.layoutAddEditAnimal.visibility = if (isUploading) View.VISIBLE else View.GONE
        binding.buttonAnimalCharity.isClickable = !isUploading
    }

    //Function to update an animal information
    private fun editAnimal(){
        viewModel.editAnimalLiveData.observe(this){
            isUploadingContent(false)
            if (it) {
                this.toast(getString(R.string.successful_edit_animal))
                val intent = Intent()
                intent.putExtra("animal", animal)
                setResult(RESULT_OK,intent)
                finish()
            }
            else { this.toast(getString(R.string.failure_edit_animal)) }

        }
    }

    //Function to add an animal information
    private fun addAnimal(){
        viewModel.createdAnimalLiveData.observe(this) {
            isUploadingContent(false)
            if (it) {
                this.toast(getString(R.string.successful_add_animal))
                finish()
            }
            else { this.toast(getString(R.string.failure_add_animal)) }
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
            if (index == 0) { isNextVisible = true; isPreVisible = false; isBtnVisible = false
            } else { isNextVisible = true; isPreVisible = true; isBtnVisible = false }
        }
        //set fragment in activity
        setFragmentView(isNextVisible, isPreVisible, isBtnVisible, pageProgress[index], pageFragments[index])
    }

    //Function to handle fragment change
    private fun setFragmentView(
        isNextVisible: Boolean, isPreVisible: Boolean, isBtnVisible: Boolean,
        progress: StateProgressBar.StateNumber, fragment: Fragment
    ) {
        showNavButton(isNextVisible, isPreVisible, isBtnVisible)
        binding.stateAddEditAnimalProgressBar.setCurrentStateNumber(progress)
        displayFragment(fragment)
    }

    //Function to handle live data from viewModel
    private fun getAnimalLiveData() {
        viewModel.nameLiveData.observe(this ,{ animal.name = it })
        viewModel.typeLiveData.observe(this ,{ animal.type = it })
        viewModel.ageLiveData.observe(this ,{ animal.age = it})
        viewModel.statusLiveData.observe(this ,{ animal.states = it })
        viewModel.genderLiveData.observe(this ,{ animal.gender = it })
        viewModel.colorLiveData.observe(this ,{ animal.color = it })
        viewModel.personalityLiveData.observe(this ,{ animal.personality = it })
        viewModel.groomingLiveData.observe(this ,{ animal.grooming = it })
        viewModel.medicalLiveData.observe(this ,{ animal.medical = it })
        viewModel.photoLiveData.observe(this ,{ animal.photoUrl = it.toString() })
        viewModel.locationLiveData.observe(this ,{
            animal.latitude = it.latitude
            animal.longitude = it.longitude
        })
    }

    //Function to add or replace a fragment view
    private fun displayFragment(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putSerializable("animal", animal)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(binding.FrameLayoutAddEditAnimal.id, fragment, ).commit()
    }

    //Function to show or hide buttons in the activity depending on the fragment
    private fun showNavButton(isNextVisible: Boolean, isPreVisible: Boolean, isBtnVisible: Boolean){
        binding.buttonAnimalCharity.visibility = isVisible(isBtnVisible)
        binding.buttonAddEditAnimalPrevious.visibility = isVisible(isPreVisible)
        binding.buttonToAddEditAnimalNext.visibility = isVisible(isNextVisible)
    }

    //Function to get the component visibility value
    private fun isVisible(visibility:Boolean) = if (visibility) View.VISIBLE else View.GONE
}