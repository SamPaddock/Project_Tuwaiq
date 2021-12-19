package com.saraha.paws.View.CharityViews.AddEditCharity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.kofigyan.stateprogressbar.StateProgressBar
import com.saraha.paws.Model.Charity
import com.saraha.paws.R
import com.saraha.paws.Util.toast
import com.saraha.paws.View.AnimalViews.AddEditAnimal.Fragment.AddEditAnimalPage1Fragment
import com.saraha.paws.View.AnimalViews.AddEditAnimal.Fragment.AddEditAnimalPage2Fragment
import com.saraha.paws.View.AnimalViews.AddEditAnimal.Fragment.AddEditAnimalPage3Fragment
import com.saraha.paws.View.CharityViews.AddEditCharity.Fragment.AddEditCharityPage1Fragment
import com.saraha.paws.View.CharityViews.AddEditCharity.Fragment.AddEditCharityPage2Fragment
import com.saraha.paws.databinding.ActivityAddEditCharityBinding

class AddEditCharityActivity : AppCompatActivity() {

    private val viewModel: AddEditCharityViewModel by viewModels()
    private lateinit var binding: ActivityAddEditCharityBinding

    private var actionType = ""
    private var charity = Charity(null,"","","",
        "","","","", "")
    var pageFragments = listOf(
        AddEditCharityPage1Fragment(),
        AddEditCharityPage2Fragment()
    )
    var pageProgress = listOf(
        StateProgressBar.StateNumber.ONE,
        StateProgressBar.StateNumber.TWO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditCharityBinding.inflate(layoutInflater)

        actionType = intent.getStringExtra("type").toString()

        if (actionType == "Edit"){
            binding.buttonCreateCharity.setText(getString(R.string.save))
            charity = intent.getSerializableExtra("charity") as Charity
        }

        //Set fragment view when activity is created
        displayFragment(AddEditCharityPage1Fragment())

        //Get data coming from viewModel that is sent from fragment
        getCharityLiveData()

        setButtonOnClickListener()

        setContentView(binding.root)
    }

    //Function to set button onClick listener
    private fun setButtonOnClickListener() {
        //set onClick listener for next button
        binding.buttonToAddEditCharityPage2.setOnClickListener {
            setFragmentView(false, pageProgress[1], pageFragments[1])
        }

        //set onClick listener for previous button
        binding.buttonAddEditCharityPage1.setOnClickListener {
            setFragmentView(true, pageProgress[0], pageFragments[0])
        }

        binding.buttonCreateCharity.setOnClickListener { verifyCharityFormFields() }
    }

    //Function to check all data is entered then send photo to FireStorage
    private fun verifyCharityFormFields() {
        if (charity.isAllDataNotEmpty()) {
            if (!Patterns.WEB_URL.matcher(charity.photo).matches()){
                viewModel.setPhotoInFireStorage(charity.photo)
                viewModel.postedPhotoLiveData.observe(this) { checkActionToPerform(it) }
            } else {
                checkActionToPerform()
            }

        } else {
            this.toast(getString(R.string.all_required))
        }
    }

    //Function to check type of activity and call action
    private fun checkActionToPerform(it: String = charity.photo) {
        if (it.isNotEmpty()) {
            if (actionType == "Edit") { editCharity(it) } else { addCharity(it) }
        }
    }

    //Function to update a charity information
    private fun editCharity(photo: String){
        viewModel.editACharityInFirebase(charity.cid!!, charity.getHashMap(photo))
        viewModel.editCharityLiveData.observe(this){
            if (it) {
                this.toast(getString(R.string.successful_edit_charity))
                finish()
            } else {
                this.toast(getString(R.string.failure_edit_charity))
            }
        }
    }

    //Function to add a charity information
    private fun addCharity(photo: String) {
        viewModel.createACharityInFirebase(charity.getHashMap(photo))

        viewModel.createdCharityLiveData.observe(this) {
            if (it) {
                this.toast(getString(R.string.successful_add_charity))
                finish()
            } else {
                this.toast(getString(R.string.failure_add_charity))
            }
        }
    }

    //Function to handle fragment change
    private fun setFragmentView(
        isVisible: Boolean,
        progress: StateProgressBar.StateNumber,
        fragment: Fragment
    ) {
        showNavButton(isVisible)
        binding.stateAddEditCharityProgressBar.setCurrentStateNumber(progress)
        displayFragment(fragment)
    }

    //Function to handle live data from viewModel
    private fun getCharityLiveData() {
        viewModel.emailLiveData.observe(this ,{ charity.email = it })
        viewModel.mobileLiveData.observe(this ,{ charity.mobile = it })
        viewModel.stcPayLiveData.observe(this ,{ charity.stcPay = it })
        viewModel.nameLiveData.observe(this ,{ charity.name = it })
        viewModel.founderLiveData.observe(this ,{ charity.founder = it })
        viewModel.facebookLinkLiveData.observe(this ,{ charity.facebookUrl = it })
        viewModel.instagramLinkLiveData.observe(this ,{ charity.instagramUrl = it })
        viewModel.photoLiveData.observe(this ,{ charity.photo = it.toString() })
    }

    //Function to add or replace a fragment view
    private fun displayFragment(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putSerializable("charity", charity)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            binding.FrameLayoutAddEditCharity.id,
            fragment,
        ).commit()
    }

    //Function to show or hide buttons in the activity depending on the fragment
    private fun showNavButton(visibility: Boolean){
        binding.buttonCreateCharity.visibility = isVisible(!visibility)
        binding.buttonAddEditCharityPage1.visibility = isVisible(!visibility)
        binding.buttonToAddEditCharityPage2.visibility = isVisible(visibility)
    }

    //Function to get the component visibility value
    private fun isVisible(visibility:Boolean) = if (visibility) View.VISIBLE else View.GONE
}