package com.saraha.paws.View.RegisterAccount

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.kofigyan.stateprogressbar.StateProgressBar
import com.saraha.paws.Model.User
import com.saraha.paws.View.RegisterAccount.Fragment.RegisterPage1Fragment
import com.saraha.paws.View.RegisterAccount.Fragment.RegisterPage2Fragment
import com.saraha.paws.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    val user = User(null,null,"","","","")
    var isPasswordCorrect = false
    var isConfirmedPasswordCorrect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        val stateProgressBar = binding.stateRegistrationProgressBar

        //Set fragment view when activity is created
        displayFragment(RegisterPage1Fragment())

        getRegistrationLiveData()

        //set onClick listener for next button
        binding.buttonToRegistrationPage2.setOnClickListener {
            showNavButton(false)
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO)
            displayFragment(RegisterPage2Fragment())
        }

        //set onClick listener for previous button
        binding.buttonRegistrationPage1.setOnClickListener {
            showNavButton(true)
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
            displayFragment(RegisterPage1Fragment())
        }

        binding.buttonCreateAccount.setOnClickListener {
            Log.d(TAG,"RegisterActivity: - onCreate: - user: ${user.email}")
            Log.d(TAG,"RegisterActivity: - onCreate: - user: ${user.mobile}")
            Log.d(TAG,"RegisterActivity: - onCreate: - user: ${user.group}")
            Log.d(TAG,"RegisterActivity: - onCreate: - user: ${user.name}")
            Log.d(TAG,"RegisterActivity: - onCreate: - user: ${isConfirmedPasswordCorrect}")
            Log.d(TAG,"RegisterActivity: - onCreate: - user: ${isPasswordCorrect}")
        }

        setContentView(binding.root)
    }

    private fun getRegistrationLiveData() {
        viewModel.emailLiveData.observe(this ,{ user.email = it })
        viewModel.mobileLiveData.observe(this ,{ user.mobile = it })
        viewModel.groupLiveData.observe(this ,{ user.group = it })
        viewModel.nameLiveData.observe(this ,{ user.name = it })
        viewModel.isConfirmedPasswordCorrect.observe(this ,{ isConfirmedPasswordCorrect = it })
        viewModel.isPasswordCorrect.observe(this ,{ isPasswordCorrect = it })
    }

    //Function to add or replace a fragment view
    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            binding.FragmentLayoutRegister.id,
            fragment
        ).commit()
    }

    //Function to show or hide buttons in the activity depending on the fragment
    private fun showNavButton(visibility: Boolean){
        binding.buttonCreateAccount.visibility = isVisible(!visibility)
        binding.buttonRegistrationPage1.visibility = isVisible(!visibility)
        binding.buttonToRegistrationPage2.visibility = isVisible(visibility)
    }

    //Function to get the component visibility value
    private fun isVisible(visibility:Boolean) = if (visibility) View.VISIBLE else View.GONE
}