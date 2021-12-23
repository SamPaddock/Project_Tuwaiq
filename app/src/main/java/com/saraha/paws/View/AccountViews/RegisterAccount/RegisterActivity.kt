package com.saraha.paws.View.AccountViews.RegisterAccount

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.kofigyan.stateprogressbar.StateProgressBar
import com.saraha.paws.Model.User
import com.saraha.paws.R
import com.saraha.paws.Util.toast
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.View.AccountViews.RegisterAccount.Fragment.RegisterPage1Fragment
import com.saraha.paws.View.AccountViews.RegisterAccount.Fragment.RegisterPage2Fragment
import com.saraha.paws.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    private val user = User(null,null,"","","","","")
    private var isPasswordCorrect = false
    private var isConfirmedPasswordCorrect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        //Set fragment view when activity is created
        displayFragment(RegisterPage1Fragment())

        //Get data coming from viewModel that is sent from fragment
        getRegistrationLiveData()

        setupToolbar()

        //set onClick listener for next button
        binding.buttonToRegistrationPage2.setOnClickListener {
            setFragmentView(false, StateProgressBar.StateNumber.TWO, RegisterPage2Fragment())
        }

        //set onClick listener for previous button
        binding.buttonRegistrationPage1.setOnClickListener {
            setFragmentView(true, StateProgressBar.StateNumber.ONE, RegisterPage1Fragment())
        }

        binding.buttonCreateAccount.setOnClickListener { verifyRegistrationFormFields() }

        setContentView(binding.root)
    }

    private fun setupToolbar() {
        val mainToolbar = binding.toolbarRegister
        mainToolbar.title = getString(R.string.create_account_msg)
        mainToolbar.setNavigationIcon(R.drawable.ic_back_24)
        setSupportActionBar(mainToolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    //Check all data is entered then send to firebase authentication
    private fun verifyRegistrationFormFields() {
        if (isPasswordCorrect && isConfirmedPasswordCorrect && user.isRegistrationDataNotEmpty()) {
            viewModel.signUpUserInFirebase(user.email, user.password!!)
            viewModel.signInResponseLiveData.observe(this) {
                createUserAccountAfterAuthentication(it)
            }
        } else {
            this.toast(getString(R.string.all_required))
        }
    }

    //after firebase authentication is complete, send the rest of the data to firebase firestorm
    private fun createUserAccountAfterAuthentication(it: Boolean) {
        if (it) {
            viewModel.createAnAccountInFirebase(user.getRegistrationHashMap())
            viewModel.createAccountResponseLiveData.observe(this) {
                if (it){
                    this.startActivity(Intent(this, HomeActivity::class.java))
                    this.finish()
                }
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
        binding.stateRegistrationProgressBar.setCurrentStateNumber(progress)
        displayFragment(fragment)
    }

    //Function to handle live data from viewModel
    private fun getRegistrationLiveData() {
        viewModel.emailLiveData.observe(this ,{ user.email = it })
        viewModel.mobileLiveData.observe(this ,{ user.mobile = it })
        viewModel.groupLiveData.observe(this ,{ user.group = it })
        viewModel.nameLiveData.observe(this ,{ user.name = it })
        viewModel.passwordLiveData.observe(this ,{ user.password = it })
        viewModel.isConfirmedPasswordCorrect.observe(this ,{ isConfirmedPasswordCorrect = it })
        viewModel.isPasswordCorrect.observe(this ,{ isPasswordCorrect = it })
    }

    //Function to add or replace a fragment view
    private fun displayFragment(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putSerializable("user", user)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            binding.FragmentLayoutRegister.id,
            fragment,
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