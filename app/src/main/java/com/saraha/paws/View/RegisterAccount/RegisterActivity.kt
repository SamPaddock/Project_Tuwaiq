package com.saraha.paws.View.RegisterAccount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.saraha.paws.R
import com.saraha.paws.View.RegisterAccount.Fragment.RegisterPage1Fragment
import com.saraha.paws.View.RegisterAccount.Fragment.RegisterPage2Fragment
import com.saraha.paws.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    //
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        //Set fragment view when activity is created
        displayFragment(RegisterPage1Fragment())

        //set onClick listener for next button
        binding.buttonToRegistrationPage2.setOnClickListener {
            showNavButton(false)
            displayFragment(RegisterPage2Fragment())
        }

        //set onClick listener for previous button
        binding.buttonRegistrationPage1.setOnClickListener {
            showNavButton(true)
            displayFragment(RegisterPage1Fragment())
        }

        setContentView(binding.root)
    }

    //Function to add or replace a fragment view
    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            binding.FragmentLayoutRegister.id,
            fragment
        ).addToBackStack(fragment.id.toString()).commit()
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