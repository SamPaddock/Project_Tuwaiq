package com.saraha.paws.View.AddEditCharity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.kofigyan.stateprogressbar.StateProgressBar
import com.saraha.paws.Model.Charity
import com.saraha.paws.R
import com.saraha.paws.View.AddEditCharity.Fragment.AddEditCharityPage1Fragment
import com.saraha.paws.View.AddEditCharity.Fragment.AddEditCharityPage2Fragment
import com.saraha.paws.View.RegisterAccount.Fragment.RegisterPage1Fragment
import com.saraha.paws.View.RegisterAccount.Fragment.RegisterPage2Fragment
import com.saraha.paws.databinding.ActivityAddEditCharityBinding

class AddEditCharityActivity : AppCompatActivity() {

    private val viewModel: AddEditCharityViewModel by viewModels()
    private lateinit var binding: ActivityAddEditCharityBinding

    private val charity = Charity(null,"","","",
        "","","","", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditCharityBinding.inflate(layoutInflater)

        //Set fragment view when activity is created
        displayFragment(AddEditCharityPage1Fragment())

        //Get data coming from viewModel that is sent from fragment
        getCharityLiveData()

        //set onClick listener for next button
        binding.buttonToAddEditCharityPage2.setOnClickListener {
            setFragmentView(false,
                StateProgressBar.StateNumber.TWO,
                AddEditCharityPage2Fragment()
            )
        }

        //set onClick listener for previous button
        binding.buttonAddEditCharityPage1.setOnClickListener {
            setFragmentView(true,
                StateProgressBar.StateNumber.ONE,
                AddEditCharityPage1Fragment())
        }

        binding.buttonCreateCharity.setOnClickListener {
            verifyCharityFormFields()
        }

        setContentView(binding.root)
    }

    private fun verifyCharityFormFields() {
        if (charity.name.isNotEmpty() && charity.email.isNotEmpty() && charity.mobile.isNotEmpty()
            && charity.stcPay.isNotEmpty() && charity.facebookUrl.isNotEmpty()
            && charity.instagramUrl.isNotEmpty() && charity.founder.isNotEmpty()
            && charity.photo.isNotEmpty()) {
            viewModel.setPhotoInFireStorage(charity.photo)
            viewModel.postedPhotoLiveData.observe(this){
                if (it.isNotEmpty()){
                    viewModel.createACharityInFirebase(hashMapOf(
                        "name" to charity.name,
                        "email" to charity.email,
                        "mobile" to charity.mobile,
                        "photo" to it,
                        "stcPay" to charity.stcPay,
                        "facebookUrl" to charity.facebookUrl,
                        "instagramUrl" to charity.instagramUrl,
                        "founder" to charity.founder,
                    ))

                    viewModel.createdCharityLiveData.observe(this){
                        if (it){
                            Toast.makeText(this,
                                "Charity successfully added", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this,
                                "Could not add the Charity", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        } else {
            Toast.makeText(this, getString(R.string.all_required), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFragmentView(
        isVisible: Boolean,
        progress: StateProgressBar.StateNumber,
        fragment: Fragment
    ) {
        showNavButton(isVisible)
        binding.stateAddEditCharityProgressBar.setCurrentStateNumber(progress)
        displayFragment(fragment)
    }

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