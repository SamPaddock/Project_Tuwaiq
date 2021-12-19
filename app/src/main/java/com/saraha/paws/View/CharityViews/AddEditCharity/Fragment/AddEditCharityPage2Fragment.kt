package com.saraha.paws.View.CharityViews.AddEditCharity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.Charity
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityActivity
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityViewModel
import com.saraha.paws.databinding.FragmentAddEditCharityPage2Binding


class AddEditCharityPage2Fragment : Fragment() {

    private lateinit var viewModel: AddEditCharityViewModel
    lateinit var binding: FragmentAddEditCharityPage2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditCharityPage2Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as AddEditCharityActivity)[AddEditCharityViewModel::class.java]

        //Get passed data from activity to set in fragment
        val charity = this.arguments?.getSerializable("charity")
        if (charity != null) setValues(charity as Charity)

        onFieldFocus()

        return binding.root
    }

    //Function to set User input when returning to fragment
    private fun setValues(charity: Charity) {
        if (charity.stcPay.isNotEmpty()){  binding.editTextAddCharityPay.setText(charity.stcPay) }
        if (charity.facebookUrl.isNotEmpty()){  binding.editTextAddCharityFacebook.setText(charity.facebookUrl) }
        if (charity.instagramUrl.isNotEmpty()){  binding.editTextAddCharityInstagram.setText(charity.instagramUrl) }
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.editTextAddCharityPay.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateMobile()
        }
        binding.editTextAddCharityFacebook.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateFacebookLink()
        }
        binding.editTextAddCharityInstagram.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateInstaLink()
        }
    }

    //Check mobile from textField and handle use cases
    private fun validateMobile() {
        val mobile = binding.editTextAddCharityPay
        val (result, isValid) = UserHelper().mobileValidation(mobile.text.toString())
        handleTextFields(mobile,result.string,0,isValid)
    }

    //Check facebook link from textField and handle use cases
    private fun validateFacebookLink() {
        val string = binding.editTextAddCharityFacebook
        val (result, isValid) = UserHelper().fieldVerification(string.text.toString())
        handleTextFields(string,result.string,1,isValid)
    }

    //Check insta link from textField and handle use cases
    private fun validateInstaLink() {
        val string = binding.editTextAddCharityInstagram
        val (result, isValid) = UserHelper().fieldVerification(string.text.toString())
        handleTextFields(string,result.string,2,isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
        } else {
            v.error = null
            when (index){
                0 -> viewModel.setCharitySTCPay(v.text.toString())
                1 -> viewModel.setCharityFacebookLink(v.text.toString())
                2 -> viewModel.setCharityInstagramLink(v.text.toString())
                else -> return
            }
        }
    }


}